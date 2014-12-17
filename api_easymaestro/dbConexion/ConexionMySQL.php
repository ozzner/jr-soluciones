<?php

header("Content-type: text/html; charset=utf-8");

class Conexion_mysql {

    private $link;
    private $error;
    private $messaje;

    function __construct() {
        include_once dirname(__FILE__) . './DBConfigMySQL.php';

        $this->error = FALSE;
    }

    public function startConexion() {

        $this->link = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

        if ($this->link->connect_errno) {

            $this->messaje = "Error " . mysqli_connect_error();
            $this->error = 1;
        } else {

            $this->messaje = "Conexión exitosa";
            $this->error = 0;
        }
    }

    public function closeConexion() {
        $this->link->close();
    }

    public function getLink() {
        return $this->link;
    }

    public function query($query) {
        $array = array();

        if (isset($query)) {

            if ($result = $this->link->query($query)) {
                $c = 0;
                while ($row = $result->fetch_assoc()) {

                    $array[$c]["tecnicoID"]        = utf8_encode($row["tecnicoID"]);
                    $array[$c]["nombre"]           = utf8_encode($row["nombre"]);
                    $array[$c]["profesion"]        = utf8_encode($row["profesion"]);
                    $array[$c]["dni"]              = utf8_encode($row["dni"]);
                    $array[$c]["direccion"]        = utf8_encode($row["direccion"]);
                    $array[$c]["celular"]          = utf8_encode($row["celular"]);
                    $array[$c]["latitud"]          = utf8_encode($row["latitud"]);
                    $array[$c]["longitud"]         = utf8_encode($row["longitud"]);
                    $array[$c]["calificacion"]     = utf8_encode($row["calificacion"]);
                    $array[$c]["comentario"]       = utf8_encode($row["comentario"]);
                    $c++;
                }
                $this->messaje = 'ok';
                $result->free();
            } else {
                $this->messaje = 'error';
            }
        }

        return $array;
    }

    /*
     * +------------------------+
     * |  Métodos informativos  |
     * +------------------------+
     */

    public function getError() {
        return $this->error;
    }

    public function getMessaje() {
        return $this->messaje;
    }

}

//$db = new Conexion_mysql();
//
//$db->startConexion();
//var_dump($db);
//print 'Estado: ' . $db->getError() . "<br>";
//print 'Mensaje: ' . $db->getMessaje();
?>