<?php
    error_reporting(E_ALL);
    ini_set('display_error',1);

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $name = $_POST['name'];
        $country = $_POST['country'];

        if(empty($name)){

            $errMSG = "이름이 입력해주세요";
        }
        else if(empty($country)){

            $errMSG = "나라를 입력해주세요";
        }
        
        if(!isset($errMSG))
        {

            try{
                $stmt = $con->prepare('INSERT INTO person(name, country) VALUES(:name, :country) ');

                $stmt->bindParam(':name', $name);
                $stmt->bindParam(':country', $country);

                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가 완료 했습니다.";
                }
                else
                {

                    $errMSG = "사용자 추가에 실패하였습니다.";
                }
                
            
            }catch(PDOException $e){

                die("데이터 베이스 에러 : ". $e->getMessage());
            }
        }
    }

?>

<?php
    if(isset($errMSG)) echo $errMSG;
    if(isset($successMSG)) echo $successMSG;

        $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if(!$android)
    {
?>

    <html>
        <body>

            <form action = "<?php $_PHP_SELF ?>" method ="POST">
                Name : <input type = "text" name = "name" />
                Country : <input type = "text" name = "country" />
                <input type = "submit" name ="submit"/>
        
            </form>

        </body>

    </html>

<?php
    }
?>