package DAWI.ProyectoTiendaD.TODO.controller;

import DAWI.ProyectoTiendaD.TODO.model.bd.Producto;
import DAWI.ProyectoTiendaD.TODO.model.dto.request.ProductoRequest;
import DAWI.ProyectoTiendaD.TODO.model.dto.response.ProductoResponse;
import DAWI.ProyectoTiendaD.TODO.service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/producto")
public class ProductoController {
    private IProductoService IProductoService;

    @GetMapping("/listado")
    public String listarProductos(Model model){
        model.addAttribute("listarproductos" ,
                IProductoService.listarProductos());
        return "usuario/formproductos";
    }

    @GetMapping("/tablaproductos")
    public String tablaProductos(Model model){
        model.addAttribute("listarproductos" ,
                IProductoService.listarProductos());
        return "usuario/formMantenimientoProductos";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Producto> listPublicaciones(){
        return IProductoService.listarProductos();
    }


    @PostMapping("/registrar")
    @ResponseBody
    public ProductoResponse registrarProducto(@RequestBody ProductoRequest productoRequest){
        String mensaje = "Producto registrado correctamente";
        boolean respuesta = true;
        try {
            Producto producto = new Producto();
            if(productoRequest.getIdproducto() > 0){
                producto.setIdproducto(productoRequest.getIdproducto());
            }
            producto.setNomidproducto(productoRequest.getNombre());
            producto.setDescproducto(productoRequest.getDescripcion());
            producto.setPrecioproducto(productoRequest.getPrecio());
            producto.setStockproducto(productoRequest.getStock());
            producto.setMarcaproducto(productoRequest.getMarca());
            producto.setImagenproducto(productoRequest.getImagen());

            IProductoService.registrarProducto(producto);
        }catch (Exception ex){
            mensaje = "Producto no registrado, error en la BD.";
            respuesta = false;
        }
        return ProductoResponse.builder().mensaje(mensaje).respuesta(respuesta).build();

    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ProductoResponse eliminarProducto(@PathVariable("id") Integer id) {
        String mensaje = "Producto eliminado correctamente";
        boolean respuesta = true;
        try {
            IProductoService.eliminarProducto(id);
        } catch (Exception ex) {
            mensaje = "Error al eliminar el producto";
            respuesta = false;
        }
        return ProductoResponse.builder().mensaje(mensaje).respuesta(respuesta).build();
    }

}
