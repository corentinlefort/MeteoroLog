<?php
require_once "MaBD.php";

class MeteoroLogAPI
{

    public $stations = array();
    public $currentWeathersStation = null;
    private $pdo = null;

    function __construct() {
        $this->pdo = MaBD::getInstance();
        //TODO : récupération des données de station météo et set dans la variable de la classe
        $stmt = $this->pdo->query("SELECT * FROM station;");
        $stationsTemp = $stmt->fetchAll(PDO::FETCH_OBJ);
        foreach ($stationsTemp as $station) {
            $this->stations[$station->rowid] = $station;
        }
    }

    function getAllStations() {
        //TODO : return au format JSON le tableau des stations
        return json_encode($this->stations);
    }

    function getStationWeathers($stationId) {
        //TODO : return au format JSON une station
        $stmt = $this->pdo->query("SELECT * FROM meteo WHERE date >= '".date("Y-m-d",time())."' AND fk_station = $stationId; ");

        $weathers = $stmt->fetchAll(PDO::FETCH_OBJ);
        $this->currentWeathersStation = array();

        foreach ($weathers as $weather) {
            array_push($this->currentWeathersStation,$weather);
        }
        return json_encode($this->currentWeathersStation);
    }

    function getCityData($cityId) {
        $stmt = $this->pdo->query("SELECT * FROM ville WHERE rowid=".$cityId.";");
        $city = $stmt->fetch(PDO::FETCH_OBJ);

        return json_encode($city);
    }
}