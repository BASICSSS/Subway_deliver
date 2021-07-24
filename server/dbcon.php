<?php

    $host = 'river97.cafe24.com';
    $dbname = 'dbadmin';
    $user = 'river97';
    $password = 'river0205';
    

    try{

    $con = new PDO("mysql:host={$host};dbname={$dbname},charset=utf8",$user,$password);

    }catch(PDOException $e){

        die("데이터 베이스 연결에 실패하였습니다". $e->getMessage());

    }


    $con -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $con -> setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

    if(function_exists('get_magic_quotes_gpc') && get_magic_quotes_gpc()){

        function undo_magic_quotes_gpc(&$array){
            foreach($array as &$value){
                if(is_array($value)){
                    undo_magic_quotes_gpc($value);

                }
                else{
                    $value = stripslashes($value);
                }
            }
        }

        undo_magic_quotes_gpc($_POST);
        undo_magic_quotes_gpc($_GET);
        undo_magic_quotes_gpc($_COOKIE);
    }

    header('Content-Type: text/html; charset=utf-8');
    

?>