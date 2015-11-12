<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Partida
 *
 * @author friendly
 */
class Partida {

    //put your code here

    private $id = 0;
    private $fecha_registro;
    private $total_segundos;
    private $total_jugadas;
    private $jugador_id;

    function __construct($fecha_registro, $total_segundos, $total_jugadas, $jugador_id) {
        $this->fecha_registro = $fecha_registro;
        $this->total_segundos = $total_segundos;
        $this->total_jugadas = $total_jugadas;
        $this->jugador_id = $jugador_id;
    }

    /**
     * funcion para guardar registro de partida
     * 
     * @global type $pdoInstance
     * @return type
     */
    public function guardar() {
        GLOBAL $pdoInstance;
        $SQL = $pdoInstance->prepare("INSERT INTO `partidas` (`total_segundos`, `total_jugadas`, `jugador_id`,`fecha_registro`) VALUES (:TOTAL_SEGUNDOS, :TOTAL_JUGADAS, :JUGADOR_ID, :FECHA_REGISTRO)");
        $SQL->execute([
            "TOTAL_SEGUNDOS" => $this->total_segundos,
            "TOTAL_JUGADAS" => $this->total_jugadas,
            "JUGADOR_ID" => $this->jugador_id,
            "FECHA_REGISTRO" => $this->fecha_registro
        ]);
        $this->id = $pdoInstance->lastInsertId();
        return $this->id > 0;
    }

    /**
     * funcion para obtener el id de registro de la partida
     * 
     * @return type
     */
    function getId() {
        return $this->id;
    }

    /**
     * funcion que obtiene todos los registros de partidas ordenadas por el mejor hasta el peor
     * 
     * nota: tener en cuenta que esto devuelve todos los registros de la tabla
     * 
     * @global type $pdoInstance
     * @return type
     */
    public static function get() {
        GLOBAL $pdoInstance;
        $SQL = $pdoInstance->prepare(
                "SELECT
                    view_partidas.id,
                    view_partidas.total_jugadas,
                    view_partidas.total_segundos,
                    view_partidas.fecha_registro,
                    view_partidas.jugador_id,
                    jugadores.nombre AS jugador_nombre,
                    jugadores.correo AS jugador_correo
                    FROM
                    view_partidas
                    INNER JOIN jugadores ON view_partidas.jugador_id = jugadores.id"
        );
        $SQL->execute();
        return $SQL->fetchAll(PDO::FETCH_OBJ);
    }

}
