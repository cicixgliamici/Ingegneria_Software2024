package org.example;

import org.example.controller.Controller;
import org.example.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args ) throws IOException, ParseException {
        Model model= new Model();
        Controller controller= new Controller(model);
    }
}
