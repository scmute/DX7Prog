/*
* [one line description of file/program]
* Copyright (C) Marcos Bernabé Serrano
*
*/

+DX7Prog{

	gui{
		var w, opX = 10, opY = 10;

		w = Window.new("DX7 Prog", Rect.new(0, 0, 500, 500), false);

		this.operator(w, op1, 10, 10);
		this.operator(w, op2, 10, 140);
		this.operator(w, op3, 10, 270);

		this.operator(w, op4, 260, 10);
		this.operator(w, op5, 260, 140);
		this.operator(w, op6, 260, 270);

		// LFO
		this.lfoGui(w, 10, 400);

		// Pitch EG
		this.pitchEGGui(w, 260, 400);

		w.front;
	}

	operator{arg w, op, opX, opY;

		var uv, view, text, but;

		uv = UserView.new(w, Rect.new(opX, opY, 300, 125));

		text = StaticText.new(uv, Rect(0, 0, 100, 15)).string = "Operator "++op.opNum;
		text.font = Font(bold: true);

		Button.new(uv, Rect(100, 0, 30, 15)).states_([["Off"], ["On"]]).action_({
			arg v;
			this.activateOp(op.opNum, v.value);
		}).value_(op.active);

		StaticText.new(uv, Rect(0,  25, 50, 15)).string = "Freq";
		StaticText.new(uv, Rect(50,  25, 20, 15)).string = "C";
		TextField.new(uv, Rect.new(60, 25, 20, 20)).action_({arg v; op.coarse_(v.value.asInt); view.refresh}).value_(op.coarse);
		//Knob.new(uv, Rect.new(60, 25, 20, 20)).action_({arg v; op.coarse_(v.value.range(0,31).round)}).value_(op.coarse/31);
		StaticText.new(uv, Rect(90,  25, 20, 15)).string = "F";
		TextField.new(uv, Rect.new(100, 25, 20, 20)).action_({arg v; op.fine_(v.value.asInt); view.refresh}).value_(op.fine);
		//Knob.new(uv, Rect.new(100, 25, 20, 20)).action_({arg v; op.fine_(v.value.range(0,99).round)}).value_(op.fine/99);
		StaticText.new(uv, Rect(130,  25, 20, 15)).string = "D";
		TextField.new(uv, Rect.new(140, 25, 20, 20)).action_({arg v; op.detune_(v.value.asInt); view.refresh}).value_(op.detune);
		//Knob.new(uv, Rect.new(140, 25, 20, 20)).action_({arg v; op.detune_(v.value.range(0,14).round)}).value_(op.detune/14);

		Button.new(uv, Rect(170, 25, 50, 20)).states_([["Ratio"], ["Fixed"]]).action_({arg v;op.oscMode_(v.value)}).value_(op.oscMode);

		StaticText.new(uv, Rect(0 , 50, 50, 15)).string = "Level";
		TextField.new(uv, Rect.new(50, 50, 20, 20)).action_({arg v; op.level1_(v.value.asInt); view.refresh}).value_(op.level1);
		TextField.new(uv, Rect.new(70, 50, 20, 20)).action_({arg v; op.level2_(v.value.asInt); view.refresh}).value_(op.level2);
		TextField.new(uv, Rect.new(90, 50, 20, 20)).action_({arg v; op.level3_(v.value.asInt); view.refresh}).value_(op.level3);
		TextField.new(uv, Rect.new(110, 50, 20, 20)).action_({arg v; op.level4_(v.value.asInt); view.refresh}).value_(op.level4);
		/* /Knob.new(uv, Rect.new(50, 50, 20, 20)).action_({arg v; op.level1_(v.value.range(0,99).round); view.refresh}).value_(op.level1/99);
		Knob.new(uv, Rect.new(70, 50, 20, 20)).action_({arg v; op.level2_(v.value.range(0,99).round); view.refresh}).value_(op.level2/99);
		Knob.new(uv, Rect.new(90, 50, 20, 20)).action_({arg v; op.level3_(v.value.range(0,99).round); view.refresh}).value_(op.level3/99);
		Knob.new(uv, Rect.new(110, 50, 20, 20)).action_({arg v; op.level4_(v.value.range(0,99).round); view.refresh}).value_(op.level4/99); */

		StaticText.new(uv, Rect(0,  75, 50, 15)).string = "Rate";
		TextField.new(uv, Rect.new(50, 75, 20, 20)).action_({arg v; op.rate1_(v.value.asInt)}).value_(op.rate1);
		TextField.new(uv, Rect.new(70, 75, 20, 20)).action_({arg v; op.rate2_(v.value.asInt)}).value_(op.rate2);
		TextField.new(uv, Rect.new(90, 75, 20, 20)).action_({arg v; op.rate3_(v.value.asInt)}).value_(op.rate3);
		TextField.new(uv, Rect.new(110, 75, 20, 20)).action_({arg v; op.rate4_(v.value.asInt)}).value_(op.rate4);

		/*Knob.new(uv, Rect.new(50, 75, 20, 20)).action_({arg v; op.rate1_(v.value.range(0,99).round)}).value_(op.rate1/99);
		Knob.new(uv, Rect.new(70, 75, 20, 20)).action_({arg v; op.rate2_(v.value.range(0,99).round)}).value_(op.rate2/99);
		Knob.new(uv, Rect.new(90, 75, 20, 20)).action_({arg v; op.rate3_(v.value.range(0,99).round)}).value_(op.rate3/99);
		Knob.new(uv, Rect.new(110, 75, 20, 20)).action_({arg v; op.rate4_(v.value.range(0,99).round)}).value_(op.rate4/99);*/

		StaticText.new(uv, Rect(0, 100, 50, 15)).string = "OutLevel";
		TextField.new(uv, Rect.new(50, 100, 20, 20)).action_({arg v; op.outLevel_(v.value.asInt)}).value_(op.outLevel);

		view = UserView(uv, Rect(140, 52, 80, 40));
		view.background_(Color.grey);

		view.drawFunc = {
			Pen.strokeColor = Color.black;

			Pen.moveTo(0@(40-(40*(op.level4/99))));
			Pen.lineTo(20@(40-(40*(op.level1/99))));
			Pen.lineTo(40@(40-(40*(op.level2/99))));
			Pen.lineTo(60@(40-(40*(op.level3/99))));
			Pen.lineTo(80@(40-(40*(op.level4/99))));
			//Pen.lineTo(100@40);

			Pen.stroke
		};
		view.refresh;

	}

	lfoGui{arg w, opX, opY;
		var text, knob1, uv;

		uv = UserView.new(w, Rect.new(opX, opY, 300, 100));

		text = StaticText.new(uv, Rect(0, 0, 50, 15)).string = "LFO";
		text.font = Font(bold: true);

		StaticText.new(uv, Rect(0, 25, 50, 15)).string = "Speed";
		TextField.new(uv, Rect.new(50, 25, 20, 20)).action_({arg v; lfo.speed_(v.value.asInt)}).value_(lfo.speed);
		//Knob.new(uv, Rect.new(50, 25, 20, 20)).action_({arg v; lfo.speed_(v.value.range(0,99))}).value_(lfo.speed/99);

		StaticText.new(uv, Rect(0, 50, 50, 15)).string = "Delay";
		TextField.new(uv, Rect.new(50, 50, 20, 20)).action_({arg v; lfo.delay_(v.value.asInt)}).value_(lfo.delay);
		//Knob.new(uv, Rect.new(50, 50, 20, 20)).action_({arg v; lfo.delay_(v.value.range(0,99))}).value_(lfo.delay/99);

		StaticText.new(uv, Rect(0, 75, 50, 15)).string = "Pitch";
		TextField.new(uv, Rect.new(50, 75, 20, 20)).action_({arg v; lfo.pitch_(v.value.asInt)}).value_(lfo.pitch);
		//Knob.new(uv, Rect.new(50, 75, 20, 20)).action_({arg v; lfo.pitch_(v.value.range(0,99))}).value_(lfo.pitch/99);

		StaticText.new(uv, Rect(0, 100, 50, 15)).string = "Amp";
		TextField.new(uv, Rect.new(50, 100, 20, 20)).action_({arg v; lfo.amp_(v.value.asInt)}).value_(lfo.amp);
		//Knob.new(uv, Rect.new(50, 100, 20, 20)).action_({arg v; lfo.amp_(v.value.range(0,99))}).value_(lfo.amp/99);

		Button.new(uv, Rect(50, 0, 60, 15)).states_([["Synch Off"], ["Synch On"]]).action_({arg v; lfo.sync_(v.value)}).value_(lfo.sync);

		Button.new(uv, Rect(70, 25, 30, 15)).states_([["TR"], ["SD"], ["SU"], ["SQ"], ["SI"], ["SH"]]).action_({arg v; lfo.wave_(v.value)}).value_(lfo.wave);
	}

	pitchEGGui{arg w, opX, opY;
		var uv, view, text;

		uv = UserView.new(w, Rect.new(opX, opY, 300, 100));

		text = StaticText.new(uv, Rect(0, 0, 100, 15)).string = "Pitch EG";
		text.font = Font(bold: true);

		StaticText.new(uv, Rect(0 , 25, 50, 15)).string = "Level";
		TextField.new(uv, Rect.new(50, 25, 20, 20)).action_({arg v; pitchEG.level1_(v.value.asInt); view.refresh}).value_(pitchEG.level1);
		TextField.new(uv, Rect.new(70, 25, 20, 20)).action_({arg v; pitchEG.level2_(v.value.asInt); view.refresh}).value_(pitchEG.level2);
		TextField.new(uv, Rect.new(90, 25, 20, 20)).action_({arg v; pitchEG.level3_(v.value.asInt); view.refresh}).value_(pitchEG.level3);
		TextField.new(uv, Rect.new(110, 25, 20, 20)).action_({arg v; pitchEG.level4_(v.value.asInt); view.refresh}).value_(pitchEG.level4);

		StaticText.new(uv, Rect(0,  50, 50, 15)).string = "Rate";
		TextField.new(uv, Rect.new(50, 50, 20, 20)).action_({arg v; pitchEG.rate1_(v.value.asInt)}).value_(pitchEG.rate1.asInt);
		TextField.new(uv, Rect.new(70, 50, 20, 20)).action_({arg v; pitchEG.rate2_(v.value.asInt)}).value_(pitchEG.rate2.asInt);
		TextField.new(uv, Rect.new(90, 50, 20, 20)).action_({arg v; pitchEG.rate3_(v.value.asInt)}).value_(pitchEG.rate3.asInt);
		TextField.new(uv, Rect.new(110, 50, 20, 20)).action_({arg v; pitchEG.rate4_(v.value.asInt)}).value_(pitchEG.rate4);

		view = UserView(uv, Rect(140, 25, 80, 40));
		view.background_(Color.grey);

		view.drawFunc = {
			Pen.strokeColor = Color.black;

			Pen.moveTo(0@(40-(40*(pitchEG.level4/99))));
			Pen.lineTo(20@(40-(40*(pitchEG.level1/99))));
			Pen.lineTo(40@(40-(40*(pitchEG.level2/99))));
			Pen.lineTo(60@(40-(40*(pitchEG.level3/99))));
			Pen.lineTo(80@(40-(40*(pitchEG.level4/99))));
			//Pen.lineTo(100@40);

			Pen.stroke
		};
		view.refresh;
	}

}