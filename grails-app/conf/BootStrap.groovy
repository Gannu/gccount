/**
  * @author : Prayag Upd
  * @since  : 27.12.2012 
  */
import eccount.User;
import eccount.Stall;

class BootStrap {
	def springSecurityService
	
	def init = { servletContext ->
		println("initialising bootstrap")
		User sandboxUser = new User(firstName:"Prayag",
			 middleName:"",			 
			 lastName:"Upd",
			 username:"prayag.upd@gmail.com",
                         password:springSecurityService.encodePassword("123456"),
                         enabled:true,
						 accountExpired:false,
						 accountLocked:false,
						 passwordExpired:false).save()
                         
		
		if(sandboxUser){
			println("sandbox user created")
			Stall stall = new Stall(name:"Estonia Food Stall", 
						        user:sandboxUser)
			println("stall created with a user")
		}else{
			println("user already exists")
		}
	}
	def destroy = {
	}
}
