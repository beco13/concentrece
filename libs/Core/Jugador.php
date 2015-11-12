<?php

/**
 * Description of Jugador
 *
 * @author friendly
 */
class Jugador {

    //put your code here

    private $id = 0;
    private $nombre;
    private $correo;
    private $clave;
    private $ultimo_ingreso;

    /**
     * Permite Crear una instancia de un jugador 
     * @param type $nombre
     * @param type $correo
     * @param type $clave
     */
    function __construct($nombre, $correo, $clave) {
        $this->nombre = $nombre;
        $this->correo = $correo;
        $this->clave = $clave;
        $this->ultimo_ingreso = date('Y-m-d H:i:s');
    }

    /**
     * funcion para registrar de un nuevo jugador
     * @global type $pdoInstance
     */
    public function registrar() {
        GLOBAL $pdoInstance;
        $SQL = $pdoInstance->prepare("INSERT INTO `jugadores` (`nombre`, `correo`, `clave`, `ultimo_ingreso`) VALUES (:NOMBRE, :CORREO, :CLAVE, :ULTIMO_INGRESO)");
        $SQL->execute([
            'NOMBRE' => $this->nombre,
            'CORREO' => $this->correo,
            'CLAVE' => sha1($this->clave),
            'ULTIMO_INGRESO' => $this->ultimo_ingreso
        ]);
        $this->id = $pdoInstance->lastInsertId();
        return $this->id > 0;
    }

    /**
     * funcion para verificar si un usario ya esta registrado mediante el correo
     * @global type $pdoInstance
     * @param type $EMAIL
     * @return type (boolean)
     */
    public static function exist($EMAIL) {
        GLOBAL $pdoInstance;
        $SQL = $pdoInstance->prepare("SELECT * FROM jugadores AS Jugador WHERE Jugador.correo = :EMAIL ");
        $SQL->execute([
            'EMAIL' => $EMAIL
        ]);
        $result = $SQL->fetchAll(PDO::FETCH_OBJ);
        return (BOOL) count($result) > 0;
    }

    /**
     * Funcion para obtener el id de registro
     *
     * @return type
     */
    function getId() {
        return $this->id;
    }

}
