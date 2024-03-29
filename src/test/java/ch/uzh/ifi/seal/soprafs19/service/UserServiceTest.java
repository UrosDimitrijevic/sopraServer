package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ch.uzh.ifi.seal.soprafs19.controller.UserController;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    //von mir hinzugefügt
    @Autowired
    private WebApplicationContext wac;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        //testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassowrd");

        User createdUser = userService.createUser(testUser);

        Assert.assertNotNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.OFFLINE);
        Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));

    }


    //von mir
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("corsConfigurer"));
    }


    //Get{ user/{id} tests
    @Test
    public void testForGetUserOne_correct() throws Exception {
        User testUser = new User();
        testUser.setUsername("Alex");
        testUser.setPassword("johoho");
        testUser.setBirthday("2001-10-10");
        userService.createUser(testUser);

        LocalDate localDate = LocalDate.now();//For reference

        this.mockMvc.perform(get("/users/{id}",testUser.getId() )).andExpect(status().isOk() ).   //+Long.toString(testUser.getId())
                andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Alex")).
                andExpect(MockMvcResultMatchers.jsonPath("$.password").value("")).
                andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("2001-10-10")).
                andExpect(MockMvcResultMatchers.jsonPath("$.token").value(testUser.getToken() )).
                andExpect(MockMvcResultMatchers.jsonPath("$.id").value( testUser.getId() )).
                andExpect(MockMvcResultMatchers.jsonPath("$.creationDate").value( localDate.format(this.formatter) ));
    }


    @Test
    public void testForGetUserOne_false() throws Exception {
        this.mockMvc.perform(get("/users/1000")).andExpect(status().isNotFound() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".username").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".birthday").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".token").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".id").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".creationDate").doesNotExist());
    }

    //Post{ user/ } test
    @Test
    public void testForPostUserHans_working() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User testUser = new User();
        testUser.setUsername("Alex");
        testUser.setPassword("johoho");
        testUser.setBirthday("2001-10-10");

        LocalDate localDate = LocalDate.now();

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Hans\", \"password\": \"123\"}")
                )
                .andExpect(status().isCreated() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".username").value("Hans"))
                .andExpect(MockMvcResultMatchers.jsonPath( ".password").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath( ".token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath( ".id").isNotEmpty() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".creationDate").value( localDate.format(this.formatter) ));

        Assert.assertEquals( "123" , userService.userByUsername("Hans").getPassword());
    }

    @Test
    public void testForPostUserPeter_taken() throws Exception {

        User testUser = new User();
        //testUser.setName("testName");
        testUser.setUsername("Peter");
        testUser.setPassword("123");

        User createdUser = userService.createUser(testUser);


        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Peter\", \"password\": \"123\"}")
        )
                .andExpect(status().isConflict() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".username").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".token").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".id").doesNotExist() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".creationDate").doesNotExist());

    }

    //PUT { user/{id} } test
    @Test
    public void testForPutUserKarl_working() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User testUser = new User();
        testUser.setUsername("Obama");
        testUser.setPassword("johoho");
        testUser.setBirthday("2001-10-10");

        User createdUser = userService.createUser(testUser);

        long id = createdUser.getId();

        String name = "Karl" + Long.toString(id);

        this.mockMvc.perform(put("/users/{id}",createdUser.getId() )
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"" + name + "\", \"birthday\": \"2012-12-24\"}")
        )
                .andExpect(status().isNoContent() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".username").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".token").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".id").doesNotExist() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".creationDate").doesNotExist());

        Assert.assertEquals( name, userService.userByID(id).getUsername() );
        Assert.assertEquals( LocalDate.parse("2012-12-24", formatter) ,userService.userByID(id).getBirthday());
    }

    @Test
    public void testForPutUserJonathan_notWorking() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User testUser = new User();
        testUser.setUsername("Putin");
        testUser.setPassword("johoho");
        testUser.setBirthday("2001-10-10");

        User createdUser = userService.createUser(testUser);

        long nonExtistingID = 1L;
        while(userService.userByID(nonExtistingID) != null){ nonExtistingID += 1L; }

        long id = createdUser.getId();

        this.mockMvc.perform(put("/users/{id}",nonExtistingID)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Karl\", \"birthday\": \"2012-12-24\"}")
        )
                .andExpect(status().isNotFound() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".username").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".token").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".id").doesNotExist() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".creationDate").doesNotExist());

    }

    @Test
    public void canGetAllUsers() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( "http://localhost:8080/users").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        //checking content
        class Response {
            public Long id;
            public String username;
            public String birthday;
            public String password;
            public String token;
            public String status;
            public String creationDate;
            public Long challenging;
            public Long gettingChalangedBy;
            Response(){}
        }
        ArrayList<Response> allUsers = new ArrayList<Response>();


        //sorting
        int i = 0;
        while( content.charAt(i) != ']') {
            i += 1;
            Response response = new Response();

            //id
            Assert.assertEquals("{\"id\":", content.substring(i,i+6) ); i += 6;
            String number = "";
            while(content.charAt(i) != ','){ number += content.charAt(i); i += 1; } i += 1;
            response.id = Long.parseLong(number);

            //username
            Assert.assertEquals("\"username\":\"", content.substring(i,i+12) ); i += 12;
            String name = "";
            while(content.charAt(i) != '\"'){ name += content.charAt(i); i += 1; } i += 2;
            response.username = name;

            //birthday
            Assert.assertEquals("\"birthday\":\"", content.substring(i,i+12) ); i += 12;
            name = "";
            while(content.charAt(i) != '\"'){ name += content.charAt(i); i += 1; } i += 2;
            response.birthday = name;

            //password
            Assert.assertEquals("\"password\":\"\",", content.substring(i,i+14) ); i += 14;

            //Token
            Assert.assertEquals("\"token\":\"", content.substring(i,i+9) ); i += 9;
            name = "";
            while(content.charAt(i) != '\"'){ name += content.charAt(i); i += 1; } i += 2;
            response.token = name;

            //Status
            Assert.assertEquals("\"status\":\"", content.substring(i,i+10) ); i += 10;
            name = "";
            while(content.charAt(i) != '\"'){ name += content.charAt(i); i += 1; } i += 2;
            response.status = name;

            //creationDate
            Assert.assertEquals("\"creationDate\":\"", content.substring(i,i+16) ); i += 16;
            name = "";
            while(content.charAt(i) != '\"'){ name += content.charAt(i); i += 1; } i += 2;
            response.creationDate = name;

            //chalenging
            Assert.assertEquals("\"challenging\":", content.substring(i,i+14) ); i += 14;
            number = "";
            while(content.charAt(i) != ','){ number += content.charAt(i); i += 1; } i += 1;
            if(number.equals("null")){ response.challenging = null; }
            else {response.challenging = Long.parseLong(number); }

            //getchallengedBy
            Assert.assertEquals("\"gettingChallengedBy\":", content.substring(i,i+22) ); i += 22;
            number = "";
            while(content.charAt(i) != '}'){ number += content.charAt(i); i += 1; } i += 1;
            if(number.equals("null")){ response.gettingChalangedBy = null; }
            else {response.gettingChalangedBy = Long.parseLong(number); }
            allUsers.add(response);
        }

        for(i = 0; i < allUsers.size(); ++i){
            Response response = allUsers.get(i);
            User user = userService.userByID(response.id);
            Assert.assertEquals(user.getUsername(),response.username);
            Assert.assertEquals(user.getToken(),response.token);
            Assert.assertEquals(user.getChallenging(),response.challenging);
            Assert.assertEquals(user.getGettingChallengedBy(), response.gettingChalangedBy);
            Assert.assertEquals(user.getStatus(),UserStatus.valueOf(response.status));
        }

    }

    @Test
    public void canLogIn() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User testUser = new User();
        testUser.setUsername("CanLogIn");
        testUser.setPassword("123");
        testUser.setStatus(UserStatus.OFFLINE);

        User createdUser = userService.createUser(testUser);

        this.mockMvc.perform(put("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"CanLogIn\", \"password\": \"123\"}")
        )
                .andExpect(status().isOk() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".username").value("CanLogIn"))
                .andExpect(MockMvcResultMatchers.jsonPath( ".password").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath( ".token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath( ".id").isNotEmpty() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".creationDate").isNotEmpty() );
        createdUser = userService.userByID(createdUser.getId());
        Assert.assertEquals(UserStatus.ONLINE,createdUser.getStatus());
    }

    @Test
    public void canNotLogIn() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User testUser = new User();
        testUser.setUsername("CanNotLogIn");
        testUser.setPassword("123");
        testUser.setStatus(UserStatus.OFFLINE);

        User createdUser = userService.createUser(testUser);

        this.mockMvc.perform(put("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"CanNotLogIn\", \"password\": \"2134\"}")
        )
                .andExpect(status().isUnauthorized() );
        createdUser = userService.userByID(createdUser.getId());
        Assert.assertEquals(UserStatus.OFFLINE,createdUser.getStatus());
    }

    public void canLogout() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User testUser = new User();
        testUser.setUsername("CanLogout");
        testUser.setPassword("123");
        testUser.setStatus(UserStatus.ONLINE);

        User createdUser = userService.createUser(testUser);

        this.mockMvc.perform(put("/users/{id}",createdUser.getId() )
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"CanLogout\", \"status\": \"OFFLINE\"}")
        )
                .andExpect(status().isNoContent() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".username").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".token").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath( ".id").doesNotExist() )
                .andExpect(MockMvcResultMatchers.jsonPath( ".creationDate").doesNotExist());

        //test if worked
        createdUser = userService.userByID(createdUser.getId());
        Assert.assertEquals(UserStatus.OFFLINE, createdUser.getStatus());
    }

    @Ignore
    @Test
    public void canChallenge() throws Exception {

        /*

        MAKE SURE ID 1 AND ID 2 EXIST

         */

        this.mockMvc.perform(put("/users/challenges/{id}", 1 )
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"2\"}") )
                .andExpect(status().isCreated() );

    }

    @Ignore
    @Test
    public void canGetChallenges() throws Exception {

        /*

        MAKE SURE ID 2 IS CHALLENGED
         */

        this.mockMvc.perform(get("/user/challengestatus/{id}", 2 )
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"2\"}") )
                .andExpect(status().isOk() );

    }
}
