package ua.lviv.trainapplogos.dto;

public class StudentResponse {
	private String imagePath;

	public StudentResponse(String imagePath) {
		this.imagePath = imagePath;
	}

	public StudentResponse() {	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}

