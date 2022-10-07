/*
 * Copyright (C) 2022 Ficohsa
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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ficohsa
 */
public class PrisonAppTest {
    
    public PrisonAppTest() {
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
     * Test of main method, of class PrisonApp.
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("################# main #################");
        String[] args = {"20","prison-example-1.txt", "test" };
        PrisonApp.main(args);
        
    }
    
    

    /**
     * Test of log method, of class PrisonApp.
     */
    @Test
    public void testLog() {
        System.out.println("################# log #################");
        String message = "Log de prueba";
        Throwable err = null;
        PrisonApp.log(message, err);
    }
    
    @Test
    public void testExecuteCMD() throws Exception{
        System.out.println("################# execteCMD #################");
        String[] args = {"20","prison-example-1.txt", "test" };
        PrisonApp.executeCMD(args);
    }
    
    
}
