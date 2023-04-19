JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Door.java \
        MagneticSensor.java \
        Sensor.java \
        Stage1.java \
        State.java \
        SwitchState.java \
        Window.java

default: classes

classes: $(CLASSES:.java=.class)

run: classes
	java Stage1 config.txt

clean:
	$(RM) *.class \
    $(RM) *.csv