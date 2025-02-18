package helpers

enum TarsStatus {
	DONE(1),
	PASSED(2),
	FAILED(3)

	final int value

	TarsStatus(int value) {
		this.value = value
	}
}
