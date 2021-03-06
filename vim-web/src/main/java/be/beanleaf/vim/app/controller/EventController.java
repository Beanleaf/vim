package be.beanleaf.vim.app.controller;

import be.beanleaf.vim.app.VimDataTable;
import be.beanleaf.vim.app.VimSession;
import be.beanleaf.vim.app.dto.EventDto;
import be.beanleaf.vim.app.utils.MessageType;
import be.beanleaf.vim.app.utils.ToastMessage;
import be.beanleaf.vim.domain.entities.Event;
import be.beanleaf.vim.services.EventService;
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
public class EventController extends VimController {

  private static final String URL_DELETE_EVENT = "/admin/event/delete";
  private static final String URL_EDIT_EVENT = "/admin/event/edit";
  private static final String URL_OVERVIEW = "/admin/events";
  private static final String URL_NEW_EVENT = "/admin/event/new";
  private static final String VIEW_EDIT_EVENT = "admin/events/editEvent";
  private static final String VIEW_OVERVIEW = "admin/events/overview";

  private final EventService eventService;
  private final VenueService venueService;

  @Autowired
  public EventController(
      VimSession vimSession,
      EventService eventService,
      VenueService venueService) {
    super(vimSession);
    this.eventService = eventService;
    this.venueService = venueService;
  }

  @GetMapping(URL_OVERVIEW)
  public String eventOverview(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) String q,
      Model model) {
    VimDataTable<Event> table = new VimDataTable<>(page, 15) {
      @Override
      public long getCount() {
        return eventService.countEvents(q);
      }

      @Override
      public List<Event> getData() {
        PageRequest page = PageRequest
            .of(getCurrentPage(), getPageSize(), eventService.getDefaultSort());
        return eventService.findAllEvents(false, page);
      }
    };

    model.addAttribute("dataTable", table);
    model.addAttribute("nameFilter", q);
    return VIEW_OVERVIEW;
  }

  @GetMapping(URL_NEW_EVENT)
  public String newEvent(Model model) {
    model.addAttribute(new EventDto());
    model.addAttribute("saleOutlets", venueService.findAll(false));
    return VIEW_EDIT_EVENT;
  }

  @PostMapping(URL_NEW_EVENT)
  public String newEventPost(
      @Valid @ModelAttribute EventDto eventDto,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes) {
    model.addAttribute(eventDto);
    model.addAttribute("saleOutlets", venueService.findAll(false));
    if (bindingResult.hasErrors()) {
      return VIEW_EDIT_EVENT;
    }

    Event event = eventService.createNewEvent(
        eventDto.getName(), eventDto.getActualStartDate(), eventDto.getActualEndDate(),
        getVimSession().getActiveUser(), eventDto.getVenue());
    eventService.save(event);
    redirectAttributes.addFlashAttribute(new ToastMessage(MessageType.SUCCESS,
        "notifications.events.newEventSuccess", true));
    return redirect(URL_OVERVIEW);
  }

  @GetMapping(URL_DELETE_EVENT + "/{id}")
  public String deleteEvent(
      @PathVariable long id,
      RedirectAttributes redirectAttributes) {
    eventService.delete(id);
    redirectAttributes.addFlashAttribute(new ToastMessage(MessageType.SUCCESS,
        "notifications.events.deleteSuccess"));
    return redirect(URL_OVERVIEW);
  }

  @GetMapping(URL_EDIT_EVENT + "/{id}")
  public String editEvent(
      @PathVariable long id,
      Model model) {
    Event event = eventService.getEvent(id);
    model.addAttribute("originalEvent", event);
    model.addAttribute("saleOutlets", venueService.findAll(false));
    model.addAttribute(new EventDto(
        event.getName(), event.getStartTime(), event.getEndTime(), event.getVenue()
    ));
    return VIEW_EDIT_EVENT;
  }

  @PostMapping(URL_EDIT_EVENT + "/{id}")
  public String editEventPost(
      @Valid @ModelAttribute EventDto eventDto,
      BindingResult bindingResult,
      @PathVariable long id,
      Model model,
      RedirectAttributes redirectAttributes
  ) {
    Event event = eventService.getEvent(id);
    model.addAttribute("originalEvent", event);
    model.addAttribute("saleOutlets", venueService.findAll(false));
    model.addAttribute(eventDto);
    if (!bindingResult.hasErrors()) {
      eventService.updateEvent(event, eventDto.getName(), eventDto.getActualStartDate(),
          eventDto.getActualEndDate(), eventDto.getVenue());
      redirectAttributes.addFlashAttribute(
          new ToastMessage(MessageType.SUCCESS,
              "notifications.events.editSuccess", true));
      return redirect(URL_OVERVIEW);
    }
    return VIEW_EDIT_EVENT;
  }
}
