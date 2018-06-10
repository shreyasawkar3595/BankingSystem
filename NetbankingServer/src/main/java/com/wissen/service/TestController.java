package com.wissen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.wissen.model.Account;
import com.wissen.model.Address;
import com.wissen.model.BeneficiaryRequest;
import com.wissen.model.BeneficiaryResponse;
import com.wissen.model.DeleteBenRequest;
import com.wissen.model.LoginRequest;
import com.wissen.model.PasswordGroup;
import com.wissen.model.Response;
import com.wissen.model.Roles;
import com.wissen.model.Status;
import com.wissen.model.Transfer;
import com.wissen.model.TransferRequest;
import com.wissen.model.Type;
import com.wissen.model.User;
import com.wissen.model.UserRequest;
import com.wissen.repository.AccountRepository;
import com.wissen.repository.TransferRepository;
import com.wissen.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RestController
public class TestController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TransferRepository transferRepository;

	@Autowired
	TransferService transferService;

	// @RequestMapping("/accounts")
	// List<Account> getAll(){
	// return (List<Account>) accountRepository.findAll();
	// }

	// @RequestMapping("/login") // TO DO
	// String login(HttpServletRequest request){
	// // ModelAndView mav= new ModelAndView();
	// // mav.addObject("login","present");
	//
	// request.getSession().setAttribute("login", "present");
	// return "Success";
	// }

	// @RequestMapping("/logOut")
	// String logOut(HttpServletRequest request,HttpServletResponse response){
	// // ModelAndView mav= new ModelAndView();
	// // mav.addObject("login","present");
	//
	// request.getSession().removeAttribute("login");
	// return "Success";
	// }

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	List<User> getAllUser(HttpServletRequest request) {

		// if(request.getSession().getAttribute("login")!=null || 1==1) {
		return (List<User>) userRepository.findAll();
		// }

		// return null;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	String removeUser() {

		User user = userRepository.findOne("dkash");
		userRepository.delete(user);
		return "successfuly added";
	}

	// @RequestMapping(value = "/addUser", method = RequestMethod.PUT, consumes =
	// "application/json")
	// Response addUser(@RequestBody UserRequest req) {
	//
	// System.out.println(user);
	// System.out.println(user.getAddress());
	// Response response = new Response();
	// user.setRole(Roles.USER);
	// user.setStatus(Status.PENDING);
	// user.getAddress().setUser(user);
	//
	// if (userRepository.findOne(user.getUserName()) == null) {
	// userRepository.save(user);
	// response.setStatus("SUCCESS");
	// response.setError("NONE");
	// } else {
	// response.setStatus("FAILED");
	// response.setError("USERNAME ALREADY USED");
	// }
	// return response;
	// }

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	User getUserDetails(@RequestParam String userName) {

		return userRepository.findOne(userName);
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.PUT, consumes = "application/json")
	Response addUser(@RequestBody UserRequest req) {

		System.out.println(req);
		System.out.println(req.getPasswordGroup());
		Response response = new Response();
		User user = new User();
		user.setUserName(req.getUserName());
		user.setFname(req.getFname());
		user.setMname(req.getLname());
		user.setLname(req.getLname());
		user.setEmail(req.getEmail());
		user.setAadharNum(req.getAadharNum());
		user.setGender(req.getGender());
		user.setPancardNum(req.getPancardNum());
		user.setPhone(req.getPhone());
		user.setAddress(req.getAddress());
		user.setPassword(req.getPasswordGroup().getPassword());
		user.setRole(Roles.USER);
		user.setStatus(Status.PENDING);

		if (userRepository.findOne(user.getUserName()) == null) {
			userRepository.save(user);
			response.setStatus("SUCCESS");
			response.setError("NONE");
		} else {
			response.setStatus("FAILED");
			response.setError("USERNAME ALREADY USED");
		}
		return response;
	}

	@RequestMapping(value = "/checkLoginDetails", consumes = "application/json", method = RequestMethod.POST) // done
	Response checkLoginDetails(@RequestBody LoginRequest loginRequest) {
		System.out.println("checkLoginDetails" + loginRequest.getPassword() + loginRequest.getUserName());
		Response response = new Response();
		User user = userRepository.findOne(loginRequest.getUserName());
		if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
			response.setStatus("SUCCESS");
			response.setError("NONE");
		} else {
			response.setStatus("FAILED");
			response.setError("INVALID USERNAME AND PASSWORD");
		}

		return response;
	}

	@RequestMapping(value = "/transfer", consumes = "application/json", method = RequestMethod.PUT)
	public Response doTransaction(@RequestBody TransferRequest transferRequest) {

		return transferService.doTransaction(transferRequest);

	}

	@RequestMapping(value = "/getAccountNumByUserName", method = RequestMethod.GET)
	List<BeneficiaryResponse> getAccountByUserName(@RequestParam(name = "username") String userName) {

		List<Account> accounts = accountRepository.findByUser(userRepository.findOne(userName));
		List<BeneficiaryResponse> accountsNum = new ArrayList<>();
		for (Account account : accounts) {
			BeneficiaryResponse bf = new BeneficiaryResponse();
			bf.setAccountNum(account.getAccNum());
			bf.setUserName(account.getUser().getUserName());
			bf.setBalance(account.getBalance());
			bf.setType(account.getType());
			bf.setOpeningDate(account.getOpeningDate());
			accountsNum.add(bf);
		}
		return accountsNum;

	}

	@RequestMapping(value = "/createBeneficiary", method = RequestMethod.PUT, consumes = "application/json")
	Response addBeneficiary(@RequestBody BeneficiaryRequest request) {

		Response response = new Response();
		String fromAccount = request.getFromAccount();
		String toAccount = request.getToAccount();
		String userName = request.getUserName();

		if (accountRepository.findOne(request.getFromAccount()).getBeneficiaries()
				.contains(accountRepository.findOne(toAccount))) {
			response.setStatus("FAILED");
			response.setError("Beneficiary already exist");
			return response;
		}

		if (accountRepository.findOne(toAccount) != null
				&& accountRepository.findOne(toAccount).getUser().getUserName().equals(userName)) {
			Account account = accountRepository.findOne(request.getFromAccount());
			account.getBeneficiaries().add(accountRepository.findOne(request.getToAccount()));
			accountRepository.save(account);
			response.setStatus("SUCCESS");
			response.setError("NONE");
		} else {
			response.setStatus("FAILED");
			response.setError("INVALID DETAILS");
		}
		return response;
	}

	@RequestMapping(value = "/getBeneficiary", method = RequestMethod.GET)
	List<BeneficiaryResponse> getBeneficiary(@RequestParam String accNum) {
		Account acc = accountRepository.findOne(accNum);
		if (acc == null)
			return new ArrayList<>();
		List<Account> accounts = acc.getBeneficiaries();

		List<BeneficiaryResponse> accountsNum = new ArrayList<>();
		for (Account account : accounts) {
			BeneficiaryResponse bf = new BeneficiaryResponse();
			bf.setAccountNum(account.getAccNum());
			bf.setUserName(account.getUser().getUserName());

			accountsNum.add(bf);
		}
		return accountsNum;
	}

	@RequestMapping(value = "/deleteBeneficiary", method = RequestMethod.GET)
	Response deleteBen(@RequestParam String account, @RequestParam String benId) {

		Response response = new Response();
		Account account1 = accountRepository.findOne(account);
		Account account2 = accountRepository.findOne(benId);

		if (account1 != null && account2 != null && account1.getBeneficiaries().contains(account2)) {
			account1.getBeneficiaries().remove(account2);
			System.out.println(account1.getBeneficiaries());
			response.setStatus("SUCCESS");
			response.setError("NONE");
			accountRepository.save(account1);
		} else {
			response.setStatus("FAILED");
			response.setError("INVALID DETAILS");
		}

		return response;
	}

	@RequestMapping(value = "/transactionList", method = RequestMethod.GET)
	List<Transfer> getTransferList(@RequestParam String accNum) {

		Account account1 = accountRepository.findOne(accNum);
		System.out.println(account1);
		System.out.println(transferRepository.findByFromAccountOrToAccountOrderByTimeStampDesc(account1, account1));
		return transferRepository.findByFromAccountOrToAccountOrderByTimeStampDesc(account1, account1);

	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
	Response deleteUser(@RequestParam String username) {
		System.out.println(username + " here");
		User u = userRepository.findByUserName(username);
		Response response = new Response();
		if (u == null) {
			response.setError("Not a valid request");
			response.setStatus("FAILED");
		} else {

			response.setError("Rejected");
			response.setStatus("NONE");
			SendEmail sendEmail = new SendEmail();
			String subject = "Infinity bank application rejected";
			String message = "Sorry your application has been rejected. Please try again with the Infinity Bank";
			sendEmail.sendEmail(u.getEmail(), subject, message);

			userRepository.delete(u);

		}
		return response;
	}

	public static String generatePassword() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 8) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public static long generateRandom(int length, Iterable<Account> iterable) {
		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}

		Long x = Long.parseLong(new String(digits));

		for (Account a : iterable) {
			if (a.getAccNum().equals(x + "")) {
				return generateRandom(12, iterable);
			}
		}
		return Long.parseLong(new String(digits));
	}

	@RequestMapping(value = "/approveUser", method = RequestMethod.GET)
	Response approveUser(@RequestParam String username) {
		User u = userRepository.findByUserName(username);
		Response response = new Response();
		if (u == null) {
			response.setError("Not a valid request");
			response.setStatus("FAILED");
		} else {
			u.setStatus(Status.ACTIVE);
			Account account = new Account();
			account.setUser(u);
			account.setOpeningDate(new Date());
			account.setAccNum(generateRandom(12, accountRepository.findAll()) + "");
			account.setBalance(0);
			account.setType(Type.SAVING);

			u.getAccounts().add(account);

			System.out.println(username + "username");

			accountRepository.save(account);
			String password = generatePassword();
			u.setPassword(password);

			userRepository.save(u);
			response.setError("Approved");
			response.setStatus("NONE");

			SendEmail sendEmail = new SendEmail();
			String subject = "Infinity bank application approved";
			String message = "Congratulations!!!" + "/n"
					+ "Your application has been approved. Welcome to the Infinity Bank" + "\n"
					+ "Your temporary password is : " + password + "\n" + "Please dont share it with anyone" + "/n"
					+ "You can change this password once you have logged in." + "\n"
					+ " Go to My Profile->Edit Profile";
			sendEmail.sendEmail(u.getEmail(), subject, message);

		}
		// u.getAccounts().add(arg0)

		return response;

	}

}
