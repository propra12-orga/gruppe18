public class AudioObject {
	static double freq = 0;
	double sample = 0;
	double amp = 0;
	int rep = 0;
	double mult = 1;

	void play() {
		freq += (int) (Math.random() * freq);
		freq = (freq) * Arena.plr.y;

		StdAudio.play(StdAudio.note(freq, sample, amp));

	}

	AudioObject(double frequency, double sampl, double amplitude) {

		freq = frequency;
		sample = sampl;
		amp = amplitude;
	}

}
