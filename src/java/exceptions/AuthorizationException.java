package exceptions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jam
 */

// Correct Username, Incorrect Password
public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String e) {
        super(e);
    }
}
