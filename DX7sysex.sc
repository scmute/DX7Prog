/*
* [one line description of file/program]
* Copyright (C) Marcos Bernabé Serrano
*
*/

DX7sysex{

	const status = 2r11110000; // Status byte - start sysex
	const id = 2r01000011; // ID # (67 - Yamaha)
	const subStatus = 2r00010000; // Substatus 1 & channel number 1
	const end = 2r11110111; // Satus - end sysex

	*sendSysEx{arg midi, group, param, value;

		midi.sysex(Int8Array[
			status, id, subStatus, group, param, value, end
		]);
	}

}