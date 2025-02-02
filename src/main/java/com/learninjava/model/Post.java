package com.learninjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Post - Model Object
 * 
 * @author learninjava.com
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	private int userId;
	
	private int id;
	
	private String title;
	
	private String body;
}
