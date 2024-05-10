package DAWI.ProyectoTiendaD.TODO.controller;

import DAWI.ProyectoTiendaD.TODO.model.bd.DetallePedido;
import DAWI.ProyectoTiendaD.TODO.model.bd.Pedido;
import DAWI.ProyectoTiendaD.TODO.model.bd.Producto;
import DAWI.ProyectoTiendaD.TODO.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    List<DetallePedido> detalles=new ArrayList<DetallePedido>();

    Pedido pedido = new Pedido();



    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) {
        log.info("Id producto enviado como parámetro {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();

        model.addAttribute("producto", producto);

        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetallePedido detallePedido = new DetallePedido();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detallePedido.setCantidadpedido(cantidad);
        detallePedido.setPrecio_unitariopedido(producto.getPrecioproducto());
        detallePedido.setNombre(producto.getNomidproducto());
        detallePedido.setSubtotalpedido(producto.getPrecioproducto() * cantidad);
        detallePedido.setProducto(producto);

        detalles.add(detallePedido);

        sumaTotal=detalles.stream().mapToDouble(dt->dt.getSubtotalpedido()).sum();
        pedido.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("pedido", pedido);


        return "usuario/carrito";
    }
}
