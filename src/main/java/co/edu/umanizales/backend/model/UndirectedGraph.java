package co.edu.umanizales.backend.model;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph extends Graph{

    @Override
    public boolean validateExistingEdge(Edge edge) {
        for(Edge ari:getEdges())
        {
            if(ari.getOrigin()==edge.getOrigin() &&
                    ari.getDestiny()==edge.getDestiny() &&
                    ari.getWeight() == edge.getWeight())
            {
                return true;
            }
            if(ari.getOrigin()==edge.getDestiny()&&
                    ari.getDestiny()==edge.getOrigin() &&
                    ari.getWeight() == edge.getWeight())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Edge> getAdjacencies(int origin) {
        List<Edge> listado= new ArrayList<>();
        for(Edge ari: getEdges())
        {
            if(ari.getOrigin()==origin || ari.getDestiny()==origin)
            {
                listado.add(ari);
            }
        }

        return listado;

    }
}