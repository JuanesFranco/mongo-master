package co.edu.umanizales.backend.model.dijkstra;

import co.edu.umanizales.backend.model.*;
import co.edu.umanizales.backend.model.exception.GraphException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Dijkstra implements Serializable{
    private short origin;
    private short destiny;
    private List<DijkstraVertex> DVertex;
    private int checks;
    private Graph graph;

    public Dijkstra(short origin, short destiny, Graph graph) {
        this.origin = origin;
        this.destiny = destiny;
        this.graph = graph;
        llenarVerticesDjikstra();
    }

    private void llenarVerticesDjikstra()
    {
        DVertex = new ArrayList<>();
        //Recorre todos los vertices del grafo
        for(Vertex vertGrafo: graph.getVertex())
        {
            //Parado en un vertice del grafo
            DijkstraVertex vertD= new DijkstraVertex(
                    vertGrafo.getCode(),null,(short)0);
            DVertex.add(vertD);
        }
    }

    public List<String> calcularDjikstra() throws GraphException
    {
        DijkstraVertex vertActual;
        //1. Pararme en el origen
        vertActual = obtenerVerticexCodigo(origin);
        // null y peso 0
        calcularDjikstra(vertActual);

        //Se si tengo o no ruta
        List<DijkstraVertex> ruta = new ArrayList<>();
        DijkstraVertex vertDestino = obtenerVerticexCodigo(destiny);
        while(vertDestino!=null)
        {
            ruta.add(vertDestino);
            vertDestino = vertDestino.getBefore();
        }
        if(ruta.size()<=1)
        {
            throw  new GraphException("No hay ruta ");
        }
        return getCitiesNames(ruta);
    }
    private void calcularDjikstra(DijkstraVertex vertActual)
    {
        /*
        2. Asigno de donde viene y peso acumulado  (Faltante)

        /*
        3. obtengo las adyancencias del vertice en el que estoy
        (Diferente si el grafo es dirigido o no dirigido
        */
        List<Edge> adyacencias = graph.getAdjacencies(vertActual.getCode());
        /*
        4. Visito todas las adyancencias
        5. Cada adyacencia actualizo su origen y peso acumulado
           cuando es menor
        */
        actualizarAdyacencias(vertActual, adyacencias);
        /*
        6.  a) Marco en el que estoy parado
             b)verificar si ya todos estan marcados (Finishing) voy al punto 8
            cuando todos vertcesD esten marcado - marcados = verticesD.size() (Faltante)
        */
        vertActual.setCheck(true);
        this.checks++;
        if(checks < graph.getVertex().size())
        {
            /*
            7. Salto a la adyacencia menor no marcada
            volver al 2
            */
            DijkstraVertex vertMenorNoVisitado=
                    getMinorAdjacencyNotVisited(adyacencias, vertActual);
            calcularDjikstra(vertMenorNoVisitado);

        }
    }

    public List<String> getCitiesNames(List<DijkstraVertex> route) {
        List<String> citiesNames = new ArrayList<>();
        for (DijkstraVertex vertex : route) {
            int cityCode = vertex.getCode();
            Vertex city = graph.getVertex().stream()
                    .filter(v -> v.getCode() == cityCode)
                    .findFirst()
                    .orElse(null);
            if (city != null) {
                citiesNames.add(" " + city.getData().getName());
            }
        }
        return citiesNames;
    }
    public DijkstraVertex obtenerVerticexCodigo(int code)
    {
        // Objetos son referencias en memoria
        for(DijkstraVertex vertD: DVertex)
        {
            if(vertD.getCode()==code)
            {
                return vertD;
            }
        }
        return null;
    }

    private void actualizarAdyacencias(DijkstraVertex actualVortex, List<Edge> adjacencies)
    {
        //Obtener las adjacencies de verticesD
        //recorriendo las aristas del grafo
        // actualizo los pesos y anterior de las adjacencies
        // si esta nulo el anterior actualizo el anterior con el vertice
        //actual
        // si no esta nulo comparo si es menor el peso acumulado para
        //actualizar
        for(Edge ari:adjacencies)
        {
            DijkstraVertex visited= obtenerVerticexCodigo(ari.getDestiny());
            //Actualizarle su origen y peso
            if(visited.getBefore()==null)
            {
                //NO ha sido visitado
                visited.setBefore(actualVortex);
                visited.setWeight((short)(actualVortex.getWeight()+ari.getWeight()));
            }
            else
            {
                short acumulatedWeight = (short)(actualVortex.getWeight()+ari.getWeight());
                if(acumulatedWeight < visited.getWeight())
                {
                    visited.setBefore(actualVortex);
                    visited.setWeight(acumulatedWeight);
                }
            }
        }

    }


    private DijkstraVertex getMinorAdjacencyNotVisited(List<Edge> adjacencies,
                                                       DijkstraVertex behind)
    {
        //Menor peso acumulado
        // cuando dos vertices tienen el mismo salta a cualquiera
        short minor = Short.MAX_VALUE;
        DijkstraVertex vertexJump = null;
        for(Edge ari: adjacencies)
        {
            int vertexCodeAnalise = 0;
            //Si mi grafo es dirigido a no es dirigido
            if(graph instanceof UndirectedGraph)
            {
                //Siempre voy a obtener el vertice que voy a analizar con el destino
                vertexCodeAnalise= ari.getOrigin();
            }
            else
            {
                // Yo tengo que determinar si debo buscar el vertice a analizar con el
                // origen de la arista o con el destino
                vertexCodeAnalise= ari.getOrigin();
                if(ari.getOrigin()== behind.getCode())
                {
                    vertexCodeAnalise= ari.getDestiny();
                }

            }
            DijkstraVertex vertAdyacente = obtenerVerticexCodigo(vertexCodeAnalise);

            if(!vertAdyacente.isCheck()) //Me interesan los que no estén marcados
            {
                if(vertAdyacente.getWeight() < minor)
                {
                    //Actualizar el menor  y marcar ese vertice como al que debo
                    //saltar
                    minor= vertAdyacente.getWeight();
                    vertexJump = vertAdyacente;
                }
            }

        }
        //Se puede presentar el bloqueo y hay que saltar a cualquiera
        if(vertexJump == null)
        {
            //Un bloqueo salto a cualquiera no marcado
            for(DijkstraVertex vertD: DVertex)
            {
                if(!vertD.isCheck())
                {
                    return vertD;
                }
            }
        }
        return vertexJump;
    }
}