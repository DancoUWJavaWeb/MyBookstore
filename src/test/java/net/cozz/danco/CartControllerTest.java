package net.cozz.danco;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "file:src/main/webapp/resources/data/books.xml",
        "file:src/main/webapp/WEB-INF/spring/*.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/*.xml"})
public class CartControllerTest {
    private static final String APPLICATION_CONTEXT_FILE_NAME = "src/main/webapp/resources/data/books.xml";
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    private MockHttpSession mockSession;

    @Mock
    private BookManager bookManager;

    @InjectMocks
    private CartController cartController;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = webAppContextSetup(wac).build();

        mockSession = new MockHttpSession(null, UUID.randomUUID().toString());
        BeanFactory beanFactory = new FileSystemXmlApplicationContext(APPLICATION_CONTEXT_FILE_NAME);
        bookManager = (BookManager) beanFactory.getBean("bookManager");
    }


    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testViewCart() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));
    }


    @Test
    public void testResponseHasCartModelAttribute() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));

        assertNull(mockSession.getAttribute("cart"));
    }


    @Test
    public void testCanAddBookToCart() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"));

        Book expectedBook = bookManager.getBooks().get(0);

        MockHttpServletRequestBuilder srb = post("/addToCart").session(mockSession)
                .param("isbn", expectedBook.getISBN());

        mockMvc.perform(srb)
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attribute("cart", hasProperty("books")));

        ShoppingCart cart = (ShoppingCart) mockSession.getAttribute("cart");
        assertNotNull(cart.getBooks());
        assertEquals(expectedBook.getTitle(), cart.getBooks().get(0).getTitle());
    }

}
