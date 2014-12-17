<?php

class MaestroDAO {

    function __construct() {
        include_once "../dbConexion/ConexionMySQL.php";
    }

    public function listarTodoMaestro() {
        $array = array();
        
        $dao = new Conexion_mysql();
        $dao->startConexion();
        
        if ($dao->getError() === 0) {
            $conn = $dao->getLink();
            $query = "Select * From "
                     ."tecnico";
            
          $array = $dao->query($query);
//          var_dump($array);
        }
//        var_dump($dao->getMessaje());
        
        if ($dao->getMessaje() != "error") {
            return $array;
        }else{
            return NULL;
        }
    }

}

?>