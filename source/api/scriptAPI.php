<?php

require_once "MeteoroLogAPI.php";

$mode = $_GET['mode'];
switch($mode) {
    case 'getWeathersStation' :
        $api = new MeteoroLogAPI();
        print $api->getStationWeathers($_GET["id"]);break;
    case 'getStationsList' :
        $api = new MeteoroLogAPI();
        print $api->getAllStations();break;
    case 'getCityData' :
        $api = new MeteoroLogAPI();
        print $api->getCityData($_GET['id']);break;
}
return 1;