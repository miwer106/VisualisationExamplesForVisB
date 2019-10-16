MACHINE QueensWithEvents
CONSTANTS n
PROPERTIES
 n : NATURAL &
 n < 121
DEFINITIONS
      SET_PREF_TIME_OUT == 6000;
      SET_PREF_CLPFD == TRUE;
      SET_PREF_MAX_INITIALISATIONS == 121;
      SET_PREF_MAX_OPERATIONS == 10;
      MAX_OPERATIONS_SetQueen == 1000;
      MAX_OPERATIONS_ChangeQueen == 1000;
      MAX_OPERATIONS_SolveAny == 4;

      is_attacked(q1) == q1:dom(queens) &
                         #q2.(q2:dom(queens) & q2 /= q1 &
                              (no_attack(q1,q2,queens) => queens(q1) = queens(q2)));
      pos_is_attacked(q1,q1row) ==
                         #q2.(q2:dom(queens) & q2 /= q1 &
                              (no_attack_pos(q1,q1row,q2,queens(q2)) => q1row = queens(q2)));
      no_attack(q1,q2,board) == no_attack_pos(q1,board(q1),q2,board(q2));
      no_attack_pos(q1,q1row,q2,q2row) == (q1row+q2-q1 /= q2row & q1row-q2+q1 /= q2row);
      Solution(board) == (
         board :  perm(1..n) /* for each column the row in which the queen is in */
         &
         !(q1,q2).(q1:1..n & q2:2..n & q2>q1 => no_attack(q1,q2,board) )
        )
VARIABLES queens
INVARIANT
  queens : (1..n) +-> (1..n)
INITIALISATION
  queens := {}
OPERATIONS
  Solve = ANY solution WHERE
      Solution(solution) &
      !x.(x:dom(queens) => solution(x)=queens(x))
  THEN
     queens := solution
  END;
  SolveFuzzy = ANY solution WHERE
      Solution(solution) &
      !x.(x:dom(queens) => solution(x):{queens(x)-1,queens(x),queens(x)+1})
  THEN
     queens := solution
  END;
  SetQueen(i,j) = SELECT i:1..n & j:1..n & i /: dom(queens) THEN
     queens(i) := j
  END;
  ChangeQueen(i,j) = SELECT i:1..n & j:1..n & i : dom(queens) & j /= queens(i) THEN
     queens(i) := j
  END;
  TryQueen(i,j) = PRE i:1..n & j:1..n THEN
      IF i /: dom(queens) THEN
          SELECT i:1..n & j:1..n & i /: dom(queens) THEN
              queens(i) := j
          END ELSE
          SELECT i:1..n & j:1..n & i : dom(queens) & j /= queens(i) THEN
              queens(i) := j
          END 
       END
    END;
  r<--Get(yy) = PRE yy:dom(queens) THEN r:= queens(yy) END
END