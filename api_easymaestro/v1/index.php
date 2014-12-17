<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
         header("Content-type: text/html; charset=utf-8");
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
    </body>
</html>
