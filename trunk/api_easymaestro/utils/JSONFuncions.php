<?php

    class Utils {
        
        function setJsonResponse($array,$error) {
        
        $arrJson = array();       
        $arrJson['error']=$error;
        $arrJson['data']=$array;

        echo json_encode($arrJson);
    } 
    
}
     

?>
