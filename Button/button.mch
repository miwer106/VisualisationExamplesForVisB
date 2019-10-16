MACHINE button
VARIABLES
  button
INVARIANT
  button:BOOL
INITIALISATION
  button := FALSE
OPERATIONS
  press_button = PRE button=FALSE THEN
    button:=TRUE
  END
END
