package net.cozz.danco;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.UUID;

import jdk.net.SocketFlow.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "file:src/main/webapp/resources/data/books.xml",
        "file:src/main/webapp/WEB-INF/spring/*.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/*.xml"})
public class UserInfoControllerTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    private MockHttpSession mockSession;

    @Mock
    private BookManager bookManager;

    @InjectMocks
    private UserInfoController userInfoController;

    private static final String testName = "Elvis";
    private static final String testEmail = "theKing@graceland.net";
    private static final String testStreetAddress = "3734 Elvis Presley Boulevard";
    private static final String testCity = "Memphis";
    private static final String testState = "TN";
    private static final String testZipCode = "38116";


    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = webAppContextSetup(wac).build();

        mockSession = new MockHttpSession(null, UUID.randomUUID().toString());
    }



    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testGetLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userInfo"))
                .andExpect(view().name("login"));
    }


    @Test
    public void testProcessForm() throws Exception {
        mockMvc.perform(post("/login").session(mockSession))
                .andExpect(status().is3xxRedirection());

        assertNotNull(mockSession.getAttribute("userInfo"));
        assertTrue(mockSession.getAttribute("userInfo") instanceof UserInfo);
    }


    @Test
    public void testProcessFormWithData() throws Exception {
        MockHttpServletRequestBuilder srb = post("/login").session(mockSession)
                .param("name", testName)
                .param("emailAddress", testEmail);

        mockMvc.perform(srb)
                .andExpect(status().is3xxRedirection());

        assertNotNull(mockSession.getAttribute("userInfo"));
        assertTrue(mockSession.getAttribute("userInfo") instanceof UserInfo);

        UserInfo userInfo = (UserInfo) mockSession.getAttribute("userInfo");
        assertEquals(userInfo.getName(), testName);
        assertEquals(userInfo.getEmailAddress(), testEmail);
    }


    @Test
    public void testShowAccount() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(testName);
        userInfo.setEmailAddress(testEmail);
        mockSession.setAttribute("userInfo", userInfo);

        mockMvc.perform(get("/account").session(mockSession))
                .andExpect(status().isOk())
                .andExpect(view().name("account"))
                .andExpect(model().attributeExists("userInfo"))
                .andExpect(model().attribute("userInfo", userInfo))
                .andExpect(model().attribute("userInfo", hasProperty("name", is(testName))))
                .andExpect(model().attribute("userInfo", hasProperty("emailAddress", is(testEmail))));
    }


    @Test
    public void testAddAddress() throws Exception {
        MockHttpServletRequestBuilder srb = post("/addAddress").session(mockSession)
                .param("streetAddress", testStreetAddress)
                .param("city", testCity)
                .param("state", testState)
                .param("zip", testZipCode);

        mockMvc.perform(srb)
                .andExpect(status().is3xxRedirection());

        UserInfo userInfo = new UserInfo();
        mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("account"))
                .andExpect(model().attributeExists("userInfo"));

        Object obj = mockSession.getAttribute("userInfo");
        assertTrue(obj instanceof UserInfo);

        userInfo = (UserInfo)  obj;
        assertNotNull(userInfo.getMailingAddresses());
        assertTrue(userInfo.getMailingAddresses().size() > 0);
        MailingAddress address = userInfo.getMailingAddresses().get(0);
        assertEquals(testStreetAddress, address.getStreetAddress());
        assertEquals(testCity, address.getCity());
        assertEquals(testState, address.getState());
        assertEquals(testZipCode, address.getZip());
    }


    @Test
    public void testInvalidAccountData() throws Exception {
        UserInfo userInfo = new UserInfo();

        MockHttpServletRequestBuilder srb = post("/updateAccount").session(mockSession)
                .param("streetAddress", testStreetAddress)
                .param("city", "")
                .param("state", "")
                .param("zip", testZipCode);

        mockMvc.perform(srb)
                .andExpect(model().attributeHasFieldErrors("userInfo"));
    }


    @Test
    public void testUpdateAccount() throws Exception {

    }
}
