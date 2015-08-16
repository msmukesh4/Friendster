<?php
	$server = 'mysql.hostinger.in';
	$user = 'u681508416_coder';
	$root = 'u681508416_image';
	$password = 'webserver123';

	$mysqli = new mysqli($server,$user,$password,$root);
	if($mysqli->maxdb_connect_errno){
		//Print when any error occers...
		echo " Failed to connect to MySQL : (" . $mysqli->maxdb_connect_errno . ")" . $mysqli->connect_error;
	}

	//Querying and storing it in $res variable
	$nameList = $mysqli->query("SELECT NAME FROM ImageList");
	
	//Starting $res From Start...
	$nameList->data_seek(0);
	$items = 0;
	$tmp_list = "";

	while ($row = $nameList->fetch_assoc()) {
		$items++;
		$tmp_list .="".$row['NAME']."//"; 		
	}
	$final = "".$items."//".$tmp_list;
	echo "".$final;
?>