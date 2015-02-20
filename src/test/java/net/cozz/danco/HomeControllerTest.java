package net.cozz.danco;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hamcrest.beans.HasProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
        "file:src/main/webapp/resources/data/books.xml",
        "file:src/main/webapp/WEB-INF/spring/*.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/*.xml"})
public class HomeControllerTest {
    private static final Logger LOGGER = Logger.getLogger(HomeControllerTest.class);
    private static final String APPLICATION_CONTEXT_FILE_NAME = "src/main/webapp/resources/data/books.xml";

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Autowired
    protected WebApplicationContext wac;

    @Mock
    private BookManager bookManager;

    @InjectMocks
    private HomeController homeController;


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
    public void testRootDocument() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }


    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", hasSize(3)))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("title", is("Neuromancer")),
                                hasProperty("author", is("William Gibson")),
                                hasProperty("genre", is("scifi")),
                                hasProperty("price", is(8.99)),
                                hasProperty("ISBN", is("978-0441569595"))
                        ))))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                                hasProperty("title", is("Foundation")),
                                hasProperty("author", is("Isaac Asimov")),
                                hasProperty("genre", is("scifi")),
                                hasProperty("price", is(14.50)),
                                hasProperty("ISBN", is("978-0553293357"))
                        ))))
                .andExpect(view().name("home"));
    }


    @Test
    public void testBookDetails() throws Exception {
        List<Book> books = bookManager.getBooks();

        Book book = books.get(0);

        MockHttpServletRequestBuilder srb = get("/details").session(mockSession)
                .param("title", book.getTitle());
        mockMvc.perform(srb)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", hasProperty("title", is(book.getTitle()))))
                .andExpect(model().attribute("book", hasProperty("description", is(book.getDescription()))));
    }

}
