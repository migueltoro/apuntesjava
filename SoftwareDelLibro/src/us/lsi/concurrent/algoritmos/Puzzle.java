package us.lsi.concurrent.algoritmos;

import java.util.*;

public interface Puzzle<P, M> {
	 P initialPosition();

	 boolean isGoal(P position);

	 Set<M> legalMoves(P position);

	 P move(P position, M move);
}
