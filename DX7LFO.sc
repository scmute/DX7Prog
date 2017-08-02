/*
* [one line description of file/program]
* Copyright (C) Marcos Bernabé Serrano
*
*/

DX7LFO{

	var midi;

	var <speed = 0, <delay = 0, <pitch = 0, <amp = 0, <sync, <wave;

	*new{arg pMidi;
		^super.new.init(pMidi);
	}

	init{arg pMidi;
		midi = pMidi;
	}

	speed_{arg value; // 0-99
		speed = value;

		if(this.fieldValid("speed", speed, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 9, speed);
		});
	}

	delay_{arg value; // 0-99
		delay = value;

		if(this.fieldValid("delay", delay, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 10, delay);
		});
	}

	pitch_{arg value; // 0-99
		pitch = value;

		if(this.fieldValid("pitch", pitch, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 11, pitch);
		});
	}

	amp_{arg value; // 0-99
		amp = value;

		if(this.fieldValid("amp", amp, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 12, amp);
		});
	}

	sync_{arg value; //0-1
		sync = value;

		if(this.fieldValid("sync", sync, 0, 1), {
			DX7sysex.sendSysEx(midi, 1, 13, sync);
		});
	}

	wave_{arg value; // 0:TR, 1:SD, 2:SU, 3:SQ, 4:SI, 5:SH
		wave = value;

		if(this.fieldValid("wave", wave, 0, 5), {
			DX7sysex.sendSysEx(midi, 1, 14, wave);
		});
	}

	fieldValid{arg name, value, min, max;

		if((value < min) || (value > max), {
			("ERROR: "++name++" must be between "++min++" and "++max).postln;
			^false;
		}, {^true});
	}

}