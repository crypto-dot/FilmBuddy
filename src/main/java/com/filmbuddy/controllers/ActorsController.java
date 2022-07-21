package com.filmbuddy.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import com.filmbuddy.models.Actor;


@Controller
@RequestMapping({"/","/actors"})
public class ActorsController {
	
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	
	@GetMapping()
	public String getAllActors(Model model ) {
		List<Actor> actors = new ArrayList<Actor>();
		Connection con;
		
		try {
			con = DriverManager.getConnection(url,username,password);
			Statement stmt = con.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT first_name,last_name,title FROM sakila.actor\n"
					+ "JOIN film_actor USING(actor_id)\n"
					+ "JOIN film USING(film_id)");
			
			while(rset.next()) {
				Actor newActor = new Actor();
				
				newActor.setFirst_name(rset.getString("first_name"));
				newActor.setLast_name(rset.getString("last_name"));
				newActor.setTitle(rset.getString("title"));
				actors.add(newActor);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}

		model.addAttribute("actors", actors);
		return "actors";
	}
	
	
	
}
