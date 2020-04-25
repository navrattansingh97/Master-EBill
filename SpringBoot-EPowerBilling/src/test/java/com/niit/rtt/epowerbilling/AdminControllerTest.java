package com.niit.rtt.epowerbilling;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.niit.rtt.epowerbilling.controller.AdminController;
import com.niit.rtt.epowerbilling.model.BillDetails;
import com.niit.rtt.epowerbilling.model.ConnectionDetails;
import com.niit.rtt.epowerbilling.model.User;
import com.niit.rtt.epowerbilling.service.BillService;
import com.niit.rtt.epowerbilling.service.ConnectionService;
import com.niit.rtt.epowerbilling.service.LoginUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {AdminController.class})
public class AdminControllerTest {
	
	@InjectMocks
	private AdminController adminController;
	
	@MockBean
	private BillService bService;
	
	@MockBean
	private ConnectionService cService;
	
	@MockBean
	private LoginUserService lService;
	
	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}
	@Spy
	ModelAndView mav;
	
	@Spy
	User user;
	
	@Spy
	HttpServletRequest req;
	
	@Spy
	ConnectionDetails con;
	
	@Spy
	BillService bservice;
	
	@Spy
	BillDetails bill;
	
	@Test
	public void saveConnTest() {
		when(req.getParameter("userId")).thenReturn("2");
		when(lService.find(2)).thenReturn(user);
		mav=adminController.saveConn(req, con);
		Assert.assertEquals("connectionDetails",mav.getViewName());
	}
	
	@Test
	public void saveBillTest() {
		when(req.getParameter("connectionId")).thenReturn("1");
		when(req.getParameter("userId")).thenReturn("1");
		when(lService.find(1)).thenReturn(user);
		
		when(cService.checkConn(1)).thenReturn(con);
		con.setConnectionId(1);
		bill.setUser(user);
		bill.setConnection(con);
	    bService.save(bill);
	    verify(bService,times(1)).save(bill);
	   
	}
	
	@Test
	public void saveUserTest()
	{	Assert.assertEquals(adminController.saveUser(user),"redirect:admin");
	}
	
	
	
	@Test
	public void showEditUserPageTest()
	{	//List<User> a=new ArrayList<User>();
		//a.add(new User("user"));
		when(lService.find(2)).thenReturn(user);
		mav=adminController.showEditUserPage(2);
		Assert.assertEquals(user,mav.getModel().get("user"));
		Assert.assertEquals("update_user",mav.getViewName());
		
	}
	@Test
	public void deleteTest()
	{
		Assert.assertEquals(adminController.delete(3),"redirect:loginUser");
	}
	
	
}