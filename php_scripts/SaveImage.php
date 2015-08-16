<?php
	$image = $_POST["image"];
	$name = $_POST["name"];

	$decodeImage = base64_decode("$image");
	file_put_contents($name . ".png", $decodeImage);

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
	echo $mysqli->host_info . "\n";

	//Querying and storing it in $res variable
	$res = $mysqli->query("SELECT ID,NAME,TIME FROM ImageList");
	echo "Result found";
	
	//Starting $res From Start...
	$res->data_seek(0);
	while ($row = $res->fetch_assoc()) {
		echo "  Name = " . $row['NAME'] . "\n" . " ID = " . $row['ID'] . " TIME = " . $row['TIME'];

	}
	
	$sql = "INSERT INTO ImageList(NAME) VALUES ('$name')";
	if ($mysqli->query($sql))
	{
		echo "Insertion Successful";
	}else{
		echo "Insertion Unsuccessful";
	}

?>