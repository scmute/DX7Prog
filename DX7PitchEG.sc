/*
* [one line description of file/program]
* Copyright (C) Marcos Bernabé Serrano
*
*/

DX7PitchEG{

	var midi;

	var <level1 = 0, <level2 = 0, <level3 = 0, <level4 = 0;
	var <rate1 = 0, <rate2 = 0, <rate3 = 0, <rate4 = 0;

	*new{arg pMidi;
		^super.new.init(pMidi);
	}

	init{arg pMidi;
		midi = pMidi;
	}

	// EG rate 1
	rate1_{arg value; // 0-99
		rate1 = value;

		if(this.fieldValid("rate1", rate1, 0, 99), {
			DX7sysex.sendSysEx(midi, 0, 126, rate1);
		});
	}

	// EG rate 2
	rate2_{arg value; // 0-99
		rate2 = value;

		if(this.fieldValid("rate2", rate2, 0, 99), {
			DX7sysex.sendSysEx(midi, 0, 127, rate2);
		});
	}

	// EG rate 3
	rate3_{arg value; // 0-99
		rate3 = value;

		if(this.fieldValid("rate3", rate3, 0, 99), {
			DX7sysex.sendSysEx(midi, 0, 128, rate3);
		});
	}

	// EG rate 4
	rate4_{arg value; // 0-99
		rate4 = value;

		if(this.fieldValid("rate4", rate4, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 1, rate4);
		});
	}

	// EG level 1
	level1_{arg value; // 0-99
		level1 = value;

		if(this.fieldValid("level1", level1, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 2, level1);
		});
	}

	// EG level 2
	level2_{arg value; // 0-99
		level2 = value;

		if(this.fieldValid("level2", level2, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 3, level2);
		});
	}

	// EG level 3
	level3_{arg value; // 0-99
		level3 = value;

		if(this.fieldValid("level3", level3, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 4, level3);
		});
	}

	// EG level 4
	level4_{arg value; // 0-99
		level4 = value;

		if(this.fieldValid("level4", level4, 0, 99), {
			DX7sysex.sendSysEx(midi, 1, 5, level4);
		});
	}

	fieldValid{arg name, value, min, max;

		if((value < min) || (value > max), {
			("ERROR: "++name++" must be between "++min++" and "++max).postln;
			^false;
		}, {^true});
	}

}