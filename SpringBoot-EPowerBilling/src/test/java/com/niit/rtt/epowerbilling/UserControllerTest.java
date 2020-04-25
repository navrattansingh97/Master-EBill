package com.niit.rtt.epowerbilling;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import com.niit.rtt.epowerbilling.controller.UserController;
import com.niit.rtt.epowerbilling.model.Address;
import com.niit.rtt.epowerbilling.model.BillDetails;
import com.niit.rtt.epowerbilling.model.Payment;
import com.niit.rtt.epowerbilling.model.User;
import com.niit.rtt.epowerbilling.service.BillService;
import com.niit.rtt.epowerbilling.service.ConnectionService;
import com.niit.rtt.epowerbilling.service.LoginUserService;
import com.niit.rtt.epowerbilling.service.UserService;
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {UserController.class})
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	@MockBean
	private UserService userService;
	@MockBean
	private LoginUserService lService;
	@MockBean
	private BillService billService;
	@MockBean
	private ConnectionService conService;
	
	@Spy
	HttpServletRequest req;
	
	@Test
	public void saveUserTest()
	{	
		User u=new User();
		u.setuId(10);
		u.setEmail("raj@test.com");
		u.setFirstName("Raj");
		u.setLastName("gs");
		u.setPassword("123");
		u.setGender("male");
		u.setAadhaar("321456987");
		u.setStatus("unapproved");
		u.setMobNumber("2222222");
		
		Address a=new Address();
		a.setState("UP");
		a.setCity("Delhi");
		a.setUser(u);
		a.setPincode(201206);
		
		u.setAddress(a);
		lService.save(u);
		verify(lService,times(1)).save(u);
		
	}
	
	@Test
	public void loginDealerTest() {
		User u=new User();
		u.setEmail("urvashitomar02@gmail.com");
		u.setPassword("123");
		when(lService.checkUser(u.getEmail())).thenReturn(u);
		
		User u1=lService.checkUser("urvashitomar02@gmail.com");
		
		Assert.assertEquals(u.getEmail(),u1.getEmail());
		Assert.assertEquals(u.getPassword(),u1.getPassword());
	}
	@Spy
	ModelAndView mav;
	
	
	@Test
	public void showUpdatePasswordTest()
	{
		User user=new User();
		user.setEmail("urvashitomar02@gmail.com");
		user.setPassword("123");
		//when(req.getParameter("email")).thenReturn("urvashitomar02@gmail.com");
		String newPassword="1234";
		when(lService.checkUser(user.getEmail())).thenReturn(user);
		
		User u1=lService.checkUser("urvashitomar02@gmail.com");
		u1=lService.find(u1.getuId());
		Assert.assertEquals(user.getuId(),u1.getuId());
		Assert.assertEquals(user.getPassword(), u1.getPassword());
		user.setPassword(newPassword);
		Assert.assertEquals(user.getPassword(), "1234");
		Assert.assertEquals("change-password",mav.getViewName());
		
	
	}
	
	@Spy
	BillDetails bill;
	
	@Test
	public void savePaymentTest() {
		when(billService.fetchBill(1)).thenReturn(bill);

		Payment p=new Payment();
		p.setNameOnCard("Urvashi");
		p.setCardNumber("123654789us");
		p.setExpMonth("04");
		p.setExpYear("2023");
		p.setAmount("200");
		p.setCvv("303");
		p.setBillDetails(bill);

		billService.save(p);
		verify(billService,times(1)).save(p);
	}
}