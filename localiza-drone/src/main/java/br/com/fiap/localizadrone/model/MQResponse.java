package br.com.fiap.localizadrone.model;

public class MQResponse {

	private String drone_id;
	private String email;
	private Float latitude;
	private Float longitude;
	private Float temperatura;
	private Float umidade;

	public String getDrone_id() {
		return drone_id;
	}

	public void setDrone_id(String drone_id) {
		this.drone_id = drone_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Float temperatura) {
		this.temperatura = temperatura;
	}

	public Float getUmidade() {
		return umidade;
	}

	public void setUmidade(Float umidade) {
		this.umidade = umidade;
	}
}
