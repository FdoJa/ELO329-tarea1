JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Door.java \
        MagneticSensor.java \
        Sensor.java \
        Stage4.java \
        State.java \
        SwitchState.java \
        Window.java \
        Siren.java \
        Central.java \
        AePlayWave.java \
        Person.java \
        PIR_detector.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	java Stage4 config.txt

clean:
	$(RM) *.class \
	$(RM) *.csv
