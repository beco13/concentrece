<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Auth
 *
 * @author friendly
 */
class Auth {
    //put your code here

    /**
     * metodo para autorizar un usuario a ingersar al sistema
     * 
     * @global type $pdoInstance
     * @param type $Email
     * @param type $Password
     * @return boolean
     */
    public function toAuthorize($Email, $Password) {
        GLOBAL $pdoInstance;

        $SQL = $pdoInstance->prepare("SELECT * FROM jugadores AS Jugador WHERE Jugador.correo = :EMAIL AND Jugador.clave = :PASS");

        $SQL->execute([
            'EMAIL' => $Email,
            'PASS' => sha1($Password),
        ]);

        $result = $SQL->fetchAll(PDO::FETCH_OBJ);

        if (count($result) === 1) {
            $this->updateLastSingin($result[0]->id);
            return $result[0];
        } else {
            return false;
        }
    }

    /**
     * methodo para actualizar la fecha del ultimo ingreso
     * 
     * @global type $pdoInstance
     * @param type $JugadorId
     */
    private function updateLastSingin($JugadorId) {
        GLOBAL $pdoInstance;
        $SQL = $pdoInstance->prepare("UPDATE `jugadores` SET `ultimo_ingreso`= :ULTIMO_INGRESO WHERE `id`= :ID ");
        $SQL->execute([
            'ULTIMO_INGRESO' => date('Y-m-d H:i:s'),
            'ID' => $JugadorId,
        ]);
    }

    /**
     * funcion para crear tokens de acceso
     * 
     * @global type $pdoInstance
     * @param type $JugadorId
     */
    public function generateAccessToken($JugadorId) {
        GLOBAL $pdoInstance;

        $dateExpire = date('Y-m-d H:i:s', strtotime("+15 minutes"));

        $token = hash("sha256", $JugadorId + $dateExpire + $JugadorId);

        $SQL = $pdoInstance->prepare("INSERT INTO `acess_tokens` (`value_token`, `jugador_id`, `expire`,`created`) VALUES (:TOKEN, :ID, :EXPIRE, :CREATED);");

        $SQL->execute([
            'ID' => $JugadorId,
            'TOKEN' => $token,
            'EXPIRE' => $dateExpire,
            'CREATED' => date('Y-m-d H:i:s')
        ]);

        return [
            'id' => $pdoInstance->lastInsertId(),
            'dateExpire' => $dateExpire,
            'token' => $token,
            'user_id' => $JugadorId
        ];
    }

    /**
     * funcion para actualizar la fecha de caducidad de un token
     * 
     * @global type $pdoInstance
     * @param type $token
     */
    public function updateToken($token) {
        GLOBAL $pdoInstance;

        $dateExpire = date('Y-m-d H:i:s', strtotime("+15 minutes"));

        $SQL = $pdoInstance->prepare("UPDATE `acess_tokens` SET `expire`= :EXPIRE WHERE `value_token`= :TOKEN ");

        $SQL->execute([
            'TOKEN' => $token,
            'EXPIRE' => $dateExpire,
        ]);
    }

}
