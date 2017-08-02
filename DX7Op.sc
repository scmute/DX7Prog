/*
* [one line description of file/program]
* Copyright (C) Marcos Bernabé Serrano
*
*/

DX7Op{

	var midi;

	var <opNum;
	var <>active = 1;

	var <level1 = 0, <level2 = 0, <level3 = 0, <level4 = 0;
	var <rate1 = 0, <rate2 = 0, <rate3 = 0, <rate4 = 0;

	var <kbdRate, <ampModSens, <keyVelSens, <outLevel = 0;

	var <oscMode, <coarse = 0, <fine = 0, <detune = 0;

	*new{arg pMidi, pOpNum;
		^super.new.init(pMidi, pOpNum);
	}

	init{arg pMidi, pOpNum;
		midi = pMidi;
		opNum = pOpNum;
	}

	// EG rate 1
	rate1_{arg value; // 0-99
		rate1 = value;

		if(this.fieldValid("rate1", rate1, 0, 99), {
			this.sendSysEx(0, rate1);
		});
	}

	// EG rate 2
	rate2_{arg value; // 0-99
		rate2 = value;

		if(this.fieldValid("rate2", rate2, 0, 99), {
			this.sendSysEx(1, rate2);
		});
	}

	// EG rate 3
	rate3_{arg value; // 0-99
		rate3 = value;

		if(this.fieldValid("rate3", rate3, 0, 99), {
			this.sendSysEx(2, rate3);
		});
	}

	// EG rate 4
	rate4_{arg value; // 0-99
		rate4 = value;

		if(this.fieldValid("rate4", rate4, 0, 99), {
			this.sendSysEx(3, rate4);
		});
	}

	// EG level 1
	level1_{arg value; // 0-99
		level1 = value;

		if(this.fieldValid("level1", level1, 0, 99), {
			this.sendSysEx(4, level1);
		});
	}

	// EG level 2
	level2_{arg value; // 0-99
		level2 = value;

		if(this.fieldValid("level2", level2, 0, 99), {
			this.sendSysEx(5, level2);
		});
	}

	// EG level 3
	level3_{arg value; // 0-99
		level3 = value;

		if(this.fieldValid("level3", level3, 0, 99), {
			this.sendSysEx(6, level3);
		});
	}

	// EG level 4
	level4_{arg value; // 0-99
		level4 = value;

		if(this.fieldValid("level4", level4, 0, 99), {
			this.sendSysEx(7, level4);
		});
	}

	// KEYBOARD RATE SCALING
	kbdRate_{arg value; // 0-7
		kbdRate = value;

		if(this.fieldValid("kbdRate", kbdRate, 0, 7), {
			this.sendSysEx(13, kbdRate);
		});
	}

	// AMP MOD SENSITIVITY
	ampModSens_{arg value; // 0-3
		ampModSens = value;

		if(this.fieldValid("ampModSens", ampModSens, 0, 3), {
			this.sendSysEx(14, ampModSens);
		});
	}

	// KEY VELOCITY SENSITIVITY
	keyVelSens_{arg value; // 0-7
		keyVelSens = value;

		if(this.fieldValid("keyVelSens", keyVelSens, 0, 7), {
			this.sendSysEx(15, keyVelSens);
		});
	}

	// OUTPUT LEVEL 0-99
	outLevel_{arg value;
		outLevel = value;

		if(this.fieldValid("outLevel", outLevel, 0, 99), {
			this.sendSysEx(16, outLevel);
		});
	}

	oscMode_{arg value; // 0-1
		oscMode = value;

		if(this.fieldValid("oscMode", oscMode, 0, 1), {
			this.sendSysEx(17, oscMode);
		});
	}

	// OP OSC FREQ COARSE
	coarse_{arg value; // 0-31
		coarse = value;

		if(this.fieldValid("coarse", coarse, 0, 31), {
			this.sendSysEx(18, coarse);
		});
	}

	// OP OSC FREQ FINE
	fine_{arg value; // 0-99
		fine = value;

		if(this.fieldValid("fine", fine, 0, 99), {
			this.sendSysEx(19, fine);
		});
	}

	// OP OSC FREQ DETUNE
	detune_{arg value; // 0-14
		detune = value;

		if(this.fieldValid("detune", detune, -7, 7), {
			this.sendSysEx(20, detune+7);
		});
	}

	// Send sysex message to DX7
	sendSysEx{arg param, value;
		var opParam = param+((6-opNum)*21);

		DX7sysex.sendSysEx(midi, 0, opParam, value);
	}

	fieldValid{arg name, value, min, max;

		if((value < min) || (value > max), {
			("ERROR: "++name++" must be between "++min++" and "++max).postln;
			^false;
		}, {^true});
	}
}