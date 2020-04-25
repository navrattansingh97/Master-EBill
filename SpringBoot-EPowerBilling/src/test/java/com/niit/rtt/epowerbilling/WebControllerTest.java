package com.niit.rtt.epowerbilling;

import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.niit.rtt.epowerbilling.controller.WebController;
import com.niit.rtt.epowerbilling.model.BillDetails;
import com.niit.rtt.epowerbilling.model.User;
import com.niit.rtt.epowerbilling.service.BillService;
import com.niit.rtt.epowerbilling.service.ConnectionService;
import com.niit.rtt.epowerbilling.service.LoginUserService;
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {WebController.class})
public class WebControllerTest {
	@InjectMocks
	private WebController webController;
	
	@MockBean
	BillService bservice;
	
	
	@MockBean
	LoginUserService loginService;
	
	@MockBean
	ConnectionService conService;
	
	@Spy
	ModelMap model;

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void viewHomePageTest()
	{
		Assert.assertEquals(webController.viewHomePage(),"index");
	}
	@Test
	public void showFormForLoginTest()
	{
		Assert.assertEquals(webController.showFormForLogin(),"login");
	}
	@Test
	public void showFormForAddTest() {
		Assert.assertEquals(webController.showFormForAdd(),"registration");
	}
	@Test
	public void viewConnDetailsPageTest()
	{
		Assert.assertEquals(webController.viewConnDetailsPage(model),"connectionDetails");
	}
	
	@Test
	public void viewBillDetailsPageTest()
	{	Assert.assertEquals(webController.viewBillDetailsPage(model),"billDetails");
		
	}
	
	@Test
	   public void indexTest() {
		Assert.assertEquals(webController.index(),"redirect:/");
	   }
	
	@Spy
	BillDetails bill;
	
	@Spy
	User user;
	
	@Spy
	ModelAndView mav;
	@Test
	public void showPaymentPageTest()
	{
		String email="navi@gmail.com";
		String billId="4";
		when(bservice.fetchBill(4)).thenReturn(bill);
		when(loginService.checkUser(email)).thenReturn(user);
		mav=webController.showPaymentPage(email, billId);
		Assert.assertEquals(user,mav.getModel().get("user"));
		Assert.assertEquals(bill,mav.getModel().get("bill"));
		Assert.assertEquals("payment",mav.getViewName());

	}
	
	
	
	
	@Spy
	HttpServletRequest req;
	
	@Spy
	 HttpSession ses;
	@Test
	public void logoutTest()
	{	// HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		 //HttpSession ses=Mockito.mock(HttpSession.class);
		 when(req.getSession()).thenReturn(ses);
		Assert.assertEquals(webController.logout(req),"index");
	}
	
}