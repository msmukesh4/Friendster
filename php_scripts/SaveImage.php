<?php
	$image = $_POST["image"];
	$name = $_POST["name"];

	if ($name!="") {
		$decodeImage = base64_decode("$image");
		file_put_contents($name . ".png", $decodeImage);
	}else{
		echo "Name is empty so no picture is saved : ";
	}
	

	// Setting the authentication and connecting the php file with the database

	$server = 'mysql.hostinger.in';
	$user = 'u681508416_coder';
	$root = 'u681508416_image';
	$password = 'webserver123';

	// Estrablishing Connection With DataBase

	$mysqli = new mysqli($server,$user,$password,$root);
	if($mysqli->maxdb_connect_errno){
		//Print when any error occers...
		echo " Failed to connect to MySQL : (" . $mysqli->maxdb_connect_errno . ")" . $mysqli->connect_error;
	}

	//checking the caption enter by user is null or not
	//if null exitting
	//else execute query
	if ($name=="") {
		echo "Name is Empty so no Insertion";	
	}else{
		$sql = "INSERT INTO ImageList(NAME) VALUES ('$name')";
		if ($mysqli->query($sql)){
			echo "1";
		}else{
		echo "Insertion Unsuccessful";
		}
	}
	
	

?>