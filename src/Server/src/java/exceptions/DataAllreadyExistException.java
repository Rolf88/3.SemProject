/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author RolfMoikjær
 */
public class DataAllreadyExistException extends Exception {

    public DataAllreadyExistException(String string) {
        super(string);
    }
}
