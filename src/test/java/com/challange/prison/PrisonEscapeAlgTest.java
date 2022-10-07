/*
 * Copyright (C) 2022 Williams Lopez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.challange.prison;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Williams Lopez
 */
public class PrisonEscapeAlgTest {
    
    
    private String def = def =   "|   |   |   |   |   |   S   |   |\n" +
                "|   P   _   |   |   _   _   _   |\n" +
                "|   |   _   _   |   _   |   _   |\n" +
                "|   v   |   _   |   _   <   _   |\n" +
                "|   _   |   _   _   _   |   _   |\n" +
                "|   _   _   _   |   _   _   _   |\n" +
                "|   |   |   |   |   |   |   |   |";;
    private  int prisonerResistance = 20;
    
    public PrisonEscapeAlgTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of canEscape method, of class PrisionEscapeAlg.
     */
    @Test
    public void testCanEscape() throws Exception {
        System.out.println("canEscape");
        PrisonEscapeAlg instance = new PrisonEscapeAlg(def, prisonerResistance);
        boolean expResult = true;
        boolean result = instance.canEscape();
        assertEquals(expResult, result);
    }

    /**
     * Test of shorterEscapeRoute method, of class PrisonEscapeAlg.
     */
    @Test
    public void testShorterEscapeRoute() {
        System.out.println("################# shorterEscapeRoute ################# ");
        PrisonEscapeAlg instance = new PrisonEscapeAlg(def, prisonerResistance);;
        String[] expResult = {"2,2", "2,3", "3,3", "3,4", "4,4", "5,4", "5,5", "5,6", "6,6", "6,7", "6,8", "5,8", "4,8", "3,8", "2,8", "2,7", "1,7"};
        String[] result = instance.shorterEscapeRoute();
        System.out.println("Array:"+Arrays.toString(result));
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of readPrisionDefinition method, of class PrisonEscapeAlg.
     */
    @Test
    public void testReadPrisionDefinition() throws Exception {
        System.out.println("################# readPrisionDefinition #################");
        String str = def;
        char[][] expResult = {
            { '|',   '|',   '|',   '|',   '|',   '|',   'S',   '|',   '|'},
            { '|',   'P',   '_',   '|',   '|',   '_',   '_',   '_',   '|'},
            { '|',   '|',   '_',   '_',   '|',   '_',   '|',   '_',   '|'},
            { '|',   'V',   '|',   '_',   '|',   '_',   '<',   '_',   '|'},
            { '|',   '_',   '|',   '_',   '_',   '_',   '|',   '_',   '|'},
            { '|',   '_',   '_',   '_',   '|',   '_',   '_',   '_',   '|'},
            { '|',   '|',   '|',   '|',   '|',   '|',   '|',   '|',   '|'}
        };
        char[][] result = PrisonEscapeAlg.readDefinition(str);
        assertArrayEquals(expResult, result);
    }
    
}
