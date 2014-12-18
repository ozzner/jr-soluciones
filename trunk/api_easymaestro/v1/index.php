        <?php
        header("Content-Type: application/json");

         include_once "../dao/dao_maestro.php";
         include_once "../utils/JSONFuncions.php";
        
         $maestro = new MaestroDAO();
         $utils =  new Utils();
         
         $array = $maestro->listarTodoMaestro();
         
         if (isset($array)){
              $utils->setJsonResponse($array,FALSE);
          }else{
              $array["mensaje"] = "Error con la consuta";
              $utils->setJsonResponse($array,TRUE);
          }
  
        ?>
