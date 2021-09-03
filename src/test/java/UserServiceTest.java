import com.dao.UserDAO;
import com.dao.User_ServiceDAO;
import com.dataBase.DBConnection;
import com.dto.Role;
import com.dto.Service;
import com.dto.User;
import com.exeptions.UserNotFoundException;
import com.model.UserService;
import com.model.User_ServiceService;
import org.junit.*;


import java.sql.SQLException;
import java.util.Optional;

public class UserServiceTest {
    private UserService userService;
    private User_ServiceService user_serviceService;
    private User testUser;
    private Service testService;

    public UserServiceTest(){
        try {
            this.userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
            this.user_serviceService = new User_ServiceService(new User_ServiceDAO(DBConnection.getDBConnection().getConnection()));
        } catch (SQLException throwables) {
            Assert.fail();
        }
        this.testUser = User.builder()
                .setName("Test")
                .setLogin("test")
                .setEmail("test@gmail.com")
                .setPassword("test123")
                .setRole(Role.USER)
                .setMoney(0);
        this.testService = Service.builder()
                .setName("Test")
                .setPrice(20);
    }

    @Test
    public void getUserByUsernameTest(){
        try {
            Assert.assertEquals(1, userService.getUserByUsername("admin").getId());
        } catch (UserNotFoundException | SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void getByIdTest() {
        try {
            Assert.assertEquals("admin", userService.getById(1).getLogin());
        } catch (SQLException | UserNotFoundException throwables) {
            Assert.fail();
        }
    }

    @Test
    public void hasUsernameTest(){
        try {
            Assert.assertTrue(userService.hasUsername("admin"));
        } catch (SQLException throwables) {
            Assert.fail();
        }
    }

    @Test
    public void hasEmailTest(){
        try {
            Assert.assertTrue(userService.hasEmail("cjj200@gmail.com"));
        } catch (SQLException throwables) {
            Assert.fail();
        }
    }

    @Test
    public void saveUserTest(){
        Optional<User> user = Optional.empty();
        try {
            user = userService.saveUser(this.testUser);
            userService.deleteById(userService.getUserByUsername(testUser.getLogin()).getId(), user_serviceService);
        } catch (SQLException | UserNotFoundException throwables) {
            Assert.fail();
        }
        Assert.assertNotNull(user.orElse(null));
        Assert.assertEquals(testUser.getLogin(), user.orElse(null).getLogin());
    }

    @Test
    public void updatePasswordTest(){
        Optional<User> user = Optional.empty();
        try {
            userService.saveUser(this.testUser);
            user = userService.updatePassword(this.testUser.getLogin(), "newtest123");
            userService.deleteById(userService.getUserByUsername(testUser.getLogin()).getId(), user_serviceService);
        } catch (SQLException | UserNotFoundException throwables) {
            Assert.fail();
        }
        Assert.assertNotNull(user.orElse(null));
        Assert.assertEquals("newtest123", user.orElse(null).getPassword());
    }

    @Test
    public void isUserIsAdminTest(){
        try {
            Assert.assertTrue(userService.isUserIsAdmin("admin"));
        } catch (UserNotFoundException | SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void getUserPasswordByUsernameTest(){
        try {
            Assert.assertEquals("123qwedcxz" ,userService.getUserPasswordByUsername("admin"));
        } catch (UserNotFoundException | SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void isBlockedTest(){
        try {
            Assert.assertFalse(userService.isBlocked(userService.getUserByUsername("admin")));
        } catch (UserNotFoundException | SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void blockUserByIdTest(){
        Optional<User> user = Optional.empty();
        try {
            userService.saveUser(this.testUser);
            user = userService.blockUserById(userService.getUserByUsername(this.testUser.getLogin()).getId());
            userService.deleteById(userService.getUserByUsername(this.testUser.getLogin()).getId(), user_serviceService);
        } catch (SQLException | UserNotFoundException throwables) {
            Assert.fail();
        }
        Assert.assertNotNull(user.orElse(null));
        Assert.assertEquals(Role.BLOCKED, user.orElse(null).getRole());
    }

    @Test
    public void unblockUserByIdTest(){
        Optional<User> user = Optional.empty();
        try {
            userService.saveUser(this.testUser);
            user = userService.unblockUserById(userService.getUserByUsername(this.testUser.getLogin()).getId());
            userService.deleteById(userService.getUserByUsername(this.testUser.getLogin()).getId(), user_serviceService);
        } catch (SQLException | UserNotFoundException throwables) {
            Assert.fail();
        }
        Assert.assertNotNull(user.orElse(null));
        Assert.assertEquals(Role.USER, user.orElse(null).getRole());
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteByIdTest() throws UserNotFoundException{
        try {
            Assert.assertNotNull(userService.saveUser(this.testUser).orElse(null));
            userService.deleteById(userService.getUserByUsername(this.testUser.getLogin()).getId(), user_serviceService);
            User user = userService.getUserByUsername(this.testUser.getLogin());
        } catch (SQLException throwables) {
            Assert.fail();
        }
    }

    @Test
    public void addMoneyByUsernameTest(){
        try {
            int startMoney = this.testUser.getMoney();
            userService.saveUser(this.testUser);
            userService.addMoneyByUsername(this.testUser.getLogin(), 20);
            User user = userService.getUserByUsername(this.testUser.getLogin());
            userService.deleteById(user.getId(), user_serviceService);
            Assert.assertEquals(user.getMoney(), startMoney + 20);
        } catch (UserNotFoundException | SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void payForServiceTest(){
        try {
            userService.saveUser(this.testUser);
            userService.addMoneyByUsername(this.testUser.getLogin(), 40);
            Optional<User> user = userService.payForService(userService.getUserByUsername(this.testUser.getLogin()), this.testService);
            userService.deleteById(userService.getUserByUsername(this.testUser.getLogin()).getId(), user_serviceService);
            Assert.assertNotNull(user.orElse(null));
            Assert.assertEquals(20, user.orElse(null).getMoney());
        } catch (SQLException | UserNotFoundException throwables) {
            Assert.fail();
        }
    }

    @Test
    public void checkEnoughMoneyTest1(){
        this.testUser.setMoney(40);
        Assert.assertTrue(userService.checkEnoughMoney(this.testUser, this.testService));
    }

    @Test
    public void checkEnoughMoneyTest2(){
        this.testUser.setMoney(10);
        Assert.assertFalse(userService.checkEnoughMoney(this.testUser, this.testService));
    }

    @Test
    public void isCorrectPasswordTest() {
        try {
            userService.saveUser(this.testUser);
            Assert.assertTrue(userService.isCorrectPassword(this.testUser.getLogin(), "test123"));
            userService.deleteById(userService.getUserByUsername("test").getId(), user_serviceService);
        } catch (UserNotFoundException | SQLException e) {
            Assert.fail();
        }
    }
}
