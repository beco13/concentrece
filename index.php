<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

require 'libs/Slim/Slim.php';
\Slim\Slim::registerAutoloader();


//define('BDNAME', "concentrece");
define('BDNAME', "concentrese");
define('USER', "root");
//define('PASS', "");
define('PASS', "C0ll1nS_1917");


$pdoInstance = new PDO('mysql:host=localhost;dbname=' . BDNAME, USER, PASS);
$pdoInstance->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$app = new \Slim\Slim(['debug' => true, 'mode' => 'development']);

require_once 'libs/Core/Auth.php';
require_once 'libs/Core/Jugador.php';
require_once 'libs/Core/Partida.php';
require_once 'libs/Core/ExceptionExtended.php';


$app->response()->header('Access-Control-Allow-Origin', '*');
$app->response()->header("Access-Control-Allow-Methods", 'GET,PUT,POST,DELETE,OPTIONS');
$app->response()->header("Access-Control-Allow-Headers", 'Origin, X-Requested-With, Content-Range, Content-Disposition, Content-Type, Access-Token');
$app->response()->header("Access-Control-Allow-Credentials", true);
$app->response()->header("Content-Type", "application/json");


$app->get('/', function() {
    
});

$app->post('/login', function() {
    $AuthInstance = new Auth();
    $userLogged = $AuthInstance->toAuthorize($_POST['correo'], $_POST['clave']);
    if ($userLogged !== false) {
        echo json_encode($AuthInstance->generateAccessToken($userLogged->id));
    } else {
        $ExectionInstance = new ExceptionExtended();
        $ExectionInstance->authFail();
    }
});

$app->get('/jugadores', function() {
    
});

$app->post('/jugadores', function() {
    $ExectionInstance = new ExceptionExtended();
    if (Jugador::exist($_POST['correo'])) {
        $ExectionInstance->userExists();
    } else {
        $JugadorInstance = new Jugador($_POST['nombre'], $_POST['correo'], $_POST['clave']);
        if ($JugadorInstance->registrar()) {
            echo json_encode([
                "user_id" => $JugadorInstance->getId()
            ]);
        } else {
            $ExectionInstance->userRegister();
        }
    }
});

$app->post('/partidas', function() {
    $PartidaInstance = new Partida($_POST['fecha_registro'], $_POST['total_segundos'], $_POST['total_jugadas'], $_POST['jugador_id']);
    if ($PartidaInstance->guardar()) {
        echo json_encode([
            "id" => $PartidaInstance->getId()
        ]);
    } else {
        $ExectionInstance = new ExceptionExtended();
        $ExectionInstance->gameRegister();
    }
});

$app->get('/partidas', function() {
    $partidas = Partida::get();
    $tmpPartidas = [];
    $tmpComparador = [];

    foreach ($partidas as $key => $partida) {
        if (in_array($partida->jugador_id, $tmpComparador)) {
            unset($partidas[$key]);
        } else {
            $tmpComparador[] = $partida->jugador_id;
            $tmpPartidas[] = $partida;
        }
    }

    echo json_encode([
        "total_rows" => count($tmpPartidas),
        "data" => $tmpPartidas
    ]);
});


    $app->run();


