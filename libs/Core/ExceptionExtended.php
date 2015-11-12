<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ExceptionExtended
 *
 * @author friendly
 */
class ExceptionExtended {
    //put your code here

    /**
     * funcion para devolver error en el login
     * 
     * @global type $app
     */
    public function authFail() {
        global $app;
        $app->response()->setStatus(400);
        echo json_encode([
            "CODE_ERROR" => "AUTH_FAIL",
        ]);
    }

    /**
     * funcion para devolver error en el registro de jugador cuando un usuario ya existe
     * 
     * @global type $app
     */
    public function userExists() {
        global $app;
        $app->response()->setStatus(409);
        echo json_encode([
            "CODE_ERROR" => "USER_EXIST",
        ]);
    }

    /**
     * funcion para devolver error en el registro de jugador
     * 
     * @global type $app
     */
    public function userRegister() {
        global $app;
        $app->response()->setStatus(400);
        echo json_encode([
            "CODE_ERROR" => "USER_REGISTER",
        ]);
    }

    /**
     * funcion para devolver error en el login post registro de jugador
     * 
     * @global type $app
     */
    public function userRegisterLogin() {
        global $app;
        $app->response()->setStatus(500);
        echo json_encode([
            "CODE_ERROR" => "USER_REGISTER_LOGIN",
        ]);
    }
    
    /**
     * funcion para devolver error en el registro de una partida
     * 
     * @global type $app
     */
    public function gameRegister() {
        global $app;
        $app->response()->setStatus(400);
        echo json_encode([
            "CODE_ERROR" => "GAME_REGISTER",
        ]);
    }

}
