(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name project.search.all.controller:SearchAllCtrl
     *
     * @description
     *
     */
    angular
        .module('project.search')
        .controller('SearchAllCtrl', SearchAllCtrl);

    function SearchAllCtrl($rootScope) {
        var vm = this;
        vm.ctrlName = 'SearchAllCtrl';

        vm.title = 'MENU_SEARCH_ALL';
        vm.isDocumentSelected = false;


        /*var response = backend.searchForAllDocuments();
        vm.documents = JSON.parse(response).documents;*/

        vm.documents = [
            {
                title: 'Don Quijote de la Mancha',
                author: 'Miguel de Cervantes Saavedra',
                user: 'josep.de.cid',
                rating: '10',
                cover: 'quijote.jpg',
                content: 'A partir de aquí se suceden numerosas aventuras, la mayor parte de las cuales terminan mal. No obstante, en la primera de ellas, don Quijote obtiene una auténtica victoria al derrotar a un joven, fuerte y pendenciero vizcaíno en un verdadero duelo a muerte, aunque pone en aprieto a una distinguida dama transeúnte en un carruaje, a quien desea proteger contra su voluntad. Pronto, amo y escudero se topan con la desgracia al ser apaleados por una turba de arrieros por causa de Rocinante, que se acercó en demasía a sus yeguas. Maltrechos, don Quijote y Sancho van a dar a una venta en donde intentan reposar. En la posada, amo y mozo protagonizan un hilarante escándalo nocturno, al confundir don Quijote en su imaginación a una desaliñada prostituta llamada Maritornes con la hija del ventero, a quien cree enamorada de él; esto despierta la cólera de un arriero, quien muele a golpes a don Quijote y a Sancho. Por la mañana, después de que don Quijote probara de su mágico bálsamo de Fierabrás, ambos se marchan, no sin antes que Sancho —con gran vergüenza suya— fuese manteado en el aire por un grupo de cardadores que se alojaban en el lugar. Luego ocurre una de las más disparatadas aventuras de don Quijote: la aventura de los rebaños de ovejas, en la cual el personaje confunde a las ovejas con dos ejércitos que se van a embestir; en su imaginación hace una prolija descripción de los principales combatientes ante el estupor de Sancho; finalmente, don Quijote toma partido y ataca a uno de los rebaños, siendo pronto derribado del caballo por los pastores. Esa noche don Quijote ataca a una procesión de enlutados monjes benedictinos que acompañaban a un ataúd a su sepultura en otra ciudad. Luego, amo y mozo velan en un bosque donde escuchan unos fuertes ruidos que inducen a don Quijote a creer que hay otros gigantes en las cercanías; aunque, realmente, son solo los golpes de unos batanes en el agua. Al día siguiente a don Quijote le ocurre la «alta aventura y rica ganancia del yelmo de Mambrino», en la cual arrebata a un barbero la famosa bacía que ha inmortalizado la representación plástica y gráfica de su figura. Luego, ocurre una nueva y grotesca aventura, en la cual don Quijote deforma hasta el extremo el ideal caballeresco de liberar a los cautivos: la liberación por la fuerza de un grupo de galeotes llevados por la justicia del rey a cumplir su pena; los galeotes, liderados por Ginés de Pasamonte, pagan muy mal el favor, apedreando a sus liberadores, con gran vergüenza de don Quijote. Don Quijote y Sancho se internan a continuación en Sierra Morena. En este lugar ocurren diversas situaciones: la extraña desaparición del Rucio, el jumento de Sancho, hecho no consignado en la primera edición y enmendado en las posteriores, aunque no satisfactoriamente. Imitando a Amadís de Gaula, don Quijote decide hacer penitencia y en cierto momento declara ante el sorprendido Sancho su secreto más íntimo: quien es en verdad Dulcinea del Toboso. Conocen a un nuevo personaje: Cardenio, quien da muestra de desquiciamiento producto de una gran frustración amorosa. Don Quijote envía a Sancho con una carta a Dulcinea, lo que obliga a este a partir en dirección al Toboso. Mientras esto ocurre, sus convecinos, el cura y el barbero, han seguido el rastro de don Quijote y en el camino se encuentran con Sancho quien regresa con su señor y le miente acerca del éxito de su viaje. También dan con una moza llamada Dorotea quien, sola, va en busca de ajustar cuentas sentimentales con el hombre que le arrebató su honra. Convencen a Dorotea de participar en un intrincado plan para devolver a don Quijote a su aldea: se hace pasar por una princesa llamada Micomicona, cuyo reino está siendo aterrorizado por un gigante. La princesa, el cura y el barbero disfrazados, se presentan ante don Quijote. La princesa le pide que la acompañe para que mate al gigante y libere a su reino. Don Quijote acepta de buen grado y todos abandonan la Sierra y llegan nuevamente a la posada en que tuvo lugar el manteamiento de Sancho. En el trascurso de este viaje, misteriosamente Sancho recupera su Rucio. En la venta confluyen una serie de personajes secundarios cuyas historias se entrelazan: Cardenio, su amada Luscinda, su ex amigo don Fernando y otros. Se confrontan y resuelven sus conflictos de orden sentimental. Por su parte don Quijote causa admiración a todos con sus discursos y su aparente discreción, pero también exaspera al ventero con sus nuevas ocurrencias: tiene lugar la famosa batalla del personaje con los cueros de vino tinto, a los que cree gigantes, y el pleito con el dueño de la bacía que la reclama airado; también don Quijote es presa de una pesada broma de parte de Maritornes y la hija del ventero, consistente en dejarlo amarrado y colgando de una mano en una de las murallas de la venta. Finalmente, todos se ponen de acuerdo en el modo de controlar a don Quijote: lo amarran y le hacen creer que ha sido encantado, y lo depositan en una jaula en la cual lo trasladan nuevamente a su aldea. Por su parte, Sancho se da cuenta del embuste, pero don Quijote no le hace caso, creyéndose hechizado. Después de algunas peripecias retornan a su pueblo donde nuevamente el protagonista es atendido por su sobrina y el ama. Hasta aquí llega la primera parte. Como epílogo, a manera de los libros de caballerías, Cervantes simula una serie de epitafios en honor de don Quijote y promete una tercera salida. En todas las aventuras, amo y escudero mantienen amenas conversaciones. Poco a poco, revelan sus personalidades y fraguan una amistad basada en el respeto mutuo, aunque Sancho claramente se da cuenta de la locura de su señor y se aprovecha de esto para deformarle la realidad, generalmente para salir de aprietos en que él lo coloca. Cervantes dedicó esta parte a Alfonso López de Zúñiga y Pérez de Guzmán, VI duque de Béjar.'
            }
        ];

        vm.select = function(document){
            vm.documentSelected = document;
            vm.isDocumentSelected = true;
        };

        vm.back = function(){
            vm.isDocumentSelected = false;
            alert(vm.isDocumentSelected);
        }
    }
}());