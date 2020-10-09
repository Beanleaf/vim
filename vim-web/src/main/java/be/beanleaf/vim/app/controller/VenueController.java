package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimDataTable;
import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.VenueDto;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.domain.entities.Venue;
import be.beanleaf.vim.services.VenueService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VenueController extends VimController {

  private static final String URL_OVERVIEW = "/admin/venues";
  private static final String VIEW_OVERVIEW = "admin/venues/overview";
  private static final String URL_NEW_VENUE = "/admin/venue/new";
  private static final String VIEW_EDIT_VENUE = "admin/venues/editVenue";
  private static final String URL_DELETE_VENUE = "/admin/venue/delete";
  private static final String URL_EDIT_VENUE = "/admin/venue/edit";

  private final VenueService venueService;

  @Autowired
  public VenueController(VimSession vimSession,
      VenueService venueService) {
    super(vimSession);
    this.venueService = venueService;
  }

  @GetMapping(URL_OVERVIEW)
  public String venuesOverview(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) String q,
      Model model
  ) {

    VimDataTable<Venue> table = new VimDataTable<>(page, 15) {
      @Override
      public long getCount() {
        return venueService.countVenues(q);
      }

      @Override
      public List<Venue> getData() {
        PageRequest page = PageRequest
            .of(getCurrentPage(), getPageSize(), venueService.getDefaultSort());
        return venueService.findVenues(q, false, page);
      }
    };

    model.addAttribute("dataTable", table);
    model.addAttribute("nameFilter", q);
    return VIEW_OVERVIEW;
  }

  @GetMapping(URL_NEW_VENUE)
  public String newVenue(Model model) {
    model.addAttribute(new VenueDto());
    return VIEW_EDIT_VENUE;
  }

  @PostMapping(URL_NEW_VENUE)
  public String newVenuePost(
      @Valid @ModelAttribute VenueDto venueDto,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes
  ) {
    model.addAttribute(venueDto);
    if (bindingResult.hasErrors()) {
      return VIEW_EDIT_VENUE;
    }
    Venue venue = venueService.createNewVenue(venueDto.getName(), venueDto.getDescription());
    venueService.save(venue);
    redirectAttributes.addFlashAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.venues.newVenueSuccess", true));
    return redirect(URL_OVERVIEW);
  }

  @GetMapping(URL_DELETE_VENUE + "/{id}")
  public String deleteVenue(
      @PathVariable long id,
      RedirectAttributes redirectAttributes
  ) {
    venueService.delete(id);
    redirectAttributes.addFlashAttribute(
        new ToastMessage(MessageType.SUCCESS, "notifications.venues.deleteSuccess"));
    return redirect(URL_OVERVIEW);
  }

  @GetMapping(URL_EDIT_VENUE + "/{id}")
  public String editVenue(
      @PathVariable long id,
      Model model
  ) {
    Venue venue = venueService.getVenue(id);
    model.addAttribute("originalVenue", venue);
    model.addAttribute(new VenueDto(venue.getName(), venue.getDescription()));
    return VIEW_EDIT_VENUE;
  }

  @PostMapping(URL_EDIT_VENUE + "/{id}")
  public String editVenuePost(
      @Valid @ModelAttribute VenueDto venueDto,
      BindingResult bindingResult,
      @PathVariable long id,
      Model model,
      RedirectAttributes redirectAttributes
  ) {
    Venue venue = venueService.getVenue(id);
    model.addAttribute("originalVenue", venue);
    model.addAttribute(venueDto);
    if (!bindingResult.hasErrors()) {
      venueService.updateVenue(venue, venueDto.getName(), venueDto.getDescription());
      redirectAttributes.addFlashAttribute(
          new ToastMessage(MessageType.SUCCESS, "notifications.venues.editSuccess", true)
      );
      return redirect(URL_OVERVIEW);
    }
    return VIEW_EDIT_VENUE;
  }
}
