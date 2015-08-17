<?php

	// 9661862952-1000;
	// 9523715386-100;	
	//contric,wipro-software dev

	$userName = $_POST['reg_name'];
	$userPassword = $_POST['reg_pass'];
	$userEmail = $_POST['reg_email'];
	$success = 1;
	$msg = "";

	// $userName = "virat";
	// $userPassword = "virat";
	// $userEmail = "Virat@gmail.com";
	
	$server = 'mysql.hostinger.in';
	$user = 'u681508416_coder';
	$root = 'u681508416_image';
	$password = 'webserver123';	

	$mysqli = new mysqli($server,$user,$password,$root);
	if($mysqli->maxdb_connect_errno){
		//Print when any error occers...
		echo " Failed to connect to MySQL : (" . $mysqli->maxdb_connect_errno . ")" . $mysqli->connect_error;
	}

	// checking that the username is already take or not
	$checkQuery = "SELECT * FROM User";
	$result = $mysqli->query($checkQuery);

	//Starting $res From Start...
	$result->data_seek(0);
	while ($row = $result->fetch_assoc()) {
		if ($row['USERNAME']==$userName) {
			$msg.="Username already Exists // ";
			$success--;
		}
		if ($row['EMAIL']==$userEmail) {
			$msg.="Email already Exists // ";
			$success--;
		}
	}
	if ($success==1) {

		$sql = "INSERT INTO User(USERNAME,EMAIL,PASSWORD) VALUES ('$userName','$userEmail','$userPassword')";
		if ($mysqli->query($sql))
		{
			echo "1";
		}else{
			echo "".$success." ".$msg;
		}
	}else{
		echo "".$success." ".$msg;
	}
	
?>