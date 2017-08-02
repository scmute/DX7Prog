/*
* [one line description of file/program]
* Copyright (C) Marcos Bernabé Serrano
*
*/

DX7Prog{

	var midi;

	var <op1, <op2, <op3, <op4, <op5, <op6;
	var <lfo, <pitchEG;
	var <algorithm, <feedback;

	*new{arg port;
		^super.new.init(port);
	}

	init{arg port;

		MIDIClient.init;
		midi = MIDIOut.new(port);

		op1 = DX7Op.new(midi, 1);
		op2 = DX7Op.new(midi, 2);
		op3 = DX7Op.new(midi, 3);
		op4 = DX7Op.new(midi, 4);
		op5 = DX7Op.new(midi, 5);
		op6 = DX7Op.new(midi, 6);

		pitchEG = DX7PitchEG.new(midi);
		lfo = DX7LFO.new(midi);
	}

	activateOp{arg opNum, activ;

		var actChain = 0;

		this.getOp(opNum).active_(activ);

		if(op6.active == 1, {actChain = 1});
		if(op5.active == 1, {actChain = actChain+2});
		if(op4.active == 1, {actChain = actChain+4});
		if(op3.active == 1, {actChain = actChain+8});
		if(op2.active == 1, {actChain = actChain+16});
		if(op1.active == 1, {actChain = actChain+32});

		actChain.asBinaryString.postln;

		DX7sysex.sendSysEx(midi, 1, 27, actChain);
	}

	getOp{arg opNum;

		case
		{opNum == 1} {^op1}
		{opNum == 2} {^op2}
		{opNum == 3} {^op3}
		{opNum == 4} {^op4}
		{opNum == 5} {^op5}
		{opNum == 6} {^op6};
	}

	algorithm_{arg value; // 0-31
		algorithm = value;
		DX7sysex.sendSysEx(midi, 1, 6, value);
	}

	feedback_{arg value; // 0-7
		feedback = value;
		DX7sysex.sendSysEx(midi, 1, 7, value);
	}

}